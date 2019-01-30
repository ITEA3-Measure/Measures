package org.measure.impl;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javassist.CtClass.voidType;


public class ExtendedWeightedClassComplexity extends DerivedMeasure {
    int weight;
    int inheritedMethodsCount;
    Map<String, String> inheritedclasses;
    Map<String, Integer> inheritedclassDepth;
    Map<String, Integer> inheritedmethods;
    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();

        inheritedclasses = new HashMap<>();
        inheritedclassDepth = new HashMap<>();
        inheritedmethods = new HashMap<>();

        String url=getProperty("URL");
        String login=getProperty("LOGIN");
        String password=getProperty("PASSWORD");
        File destinationFolder= new File("projectTarget");
        try {
            final SVNURL svnurl = SVNURL.parseURIEncoded(url);
            System.out.println(svnurl);

            final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
            BasicAuthenticationManager basicAuthenticationManager = new BasicAuthenticationManager(login, password);
            svnOperationFactory.setAuthenticationManager(basicAuthenticationManager);
            SVNUpdateClient svnUpdateClient = new SVNUpdateClient(basicAuthenticationManager,null);
            svnUpdateClient.doCheckout(svnurl,destinationFolder,null,null,true);

        } catch (SVNException e) {
            e.printStackTrace();
        }

        weight=0;
        classCheck(destinationFolder);
        computeInheritanceDepthWeight();
        deleteDir(destinationFolder);

        //recupération de la mesure dépendante
        List<IMeasurement> wcc = getMeasureInputByRole("WeightedClassComplexity A");

        //resultat
        int finalWeight=weight+(Integer)wcc.get(0).getValues().get("value");

        //intégration du résultat final
        IntegerMeasurement weightmeasured=new IntegerMeasurement();
        weightmeasured.setValue(finalWeight);

        result.add(weightmeasured);

        return result;
    }

    private void classCheck(File files) {
        if (files.isDirectory()) {
            for (File child : files.listFiles()) {
                if (child.isFile() & child.getName().endsWith(".java")) {
                    fileParse(child);
                } else {
                    classCheck(child);
                }
            }

        }else if(files.isFile() & files.getName().endsWith(".java")){
            fileParse(files);
        }
    }

    public void fileParse(File classFile) {
        try {
            //System.out.println(classFile.getAbsolutePath());
            CompilationUnit ast= JavaParser.parse(classFile);
            List<ClassOrInterfaceDeclaration> clas = ast.getNodesByType(ClassOrInterfaceDeclaration.class);
            List<MethodDeclaration> methods = ast.getNodesByType(MethodDeclaration.class);
            if(clas.size()>0) {
                checkInheritance(clas,methods);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void countOverrideMethods(List<MethodDeclaration> methods){

        inheritedMethodsCount=0;
        for (Node method : methods) {
            MarkerAnnotationExpr override = new MarkerAnnotationExpr(new Name("Override"));
            // System.out.println(method.getChildNodes().get(0) instanceof MarkerAnnotationExpr);
            if (method.getChildNodes().contains(override)) {
                    inheritedMethodsCount++;
            }
        }
    }
    public void checkInheritance(List<ClassOrInterfaceDeclaration> clas,List<MethodDeclaration> methods) {

        String className = clas.get(0).getNameAsString();

        //check inheritance methods
        countOverrideMethods(methods);
        inheritedmethods.put(className,inheritedMethodsCount);
        //check inheritance classes
        if (clas.get(0).getExtendedTypes().size() > 0) {
            String name = clas.get(0).getExtendedTypes().get(0).getNameAsString();
            inheritedclasses.put(className, name);
        } else {
            inheritedclasses.put(className, "");
        }

    }

    public void computeInheritanceDepthWeight(){

        for (Map.Entry<String, String> entry : inheritedclasses.entrySet()) {
            int count=0;
            int depth = computeInheritanceDepth(entry.getValue(),count);
            inheritedclassDepth.put(entry.getKey(),depth);
        }

        int inheritanceWeight;
        for (Map.Entry<String,Integer> entry : inheritedclassDepth.entrySet()){
            if(inheritedmethods.get(entry.getKey()) == 0){
                inheritanceWeight = (entry.getValue()*entry.getValue());
            }else{
                inheritanceWeight = (entry.getValue()*entry.getValue())*inheritedmethods.get(entry.getKey());
            }

            weight=weight+inheritanceWeight;
        }

    }


    public int computeInheritanceDepth(String extendedClass, int count){
        //count = count+1;
        count++;
        if(inheritedclasses.containsKey(extendedClass)){
            if(inheritedclasses.get(extendedClass) == ""){
                //depth = count;
            }else{
                count = computeInheritanceDepth(inheritedclasses.get(extendedClass), count);

            }
        }
        return count;
    }


    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                    return false;
            }
        }
        return dir.delete();
    }
/*
    private static void classCheckbis(File files) {
        if (files.isDirectory()) {
            for (File child : files.listFiles()) {
                if (child.isFile() & child.getName().endsWith(".java")) {
                    fileParsebis(child);
                } else {
                    classCheckbis(child);
                }
            }

        }else if(files.isFile() & files.getName().endsWith(".java")){
            fileParsebis(files);
        }
    }

    public static void fileParsebis(File classFile) {
        try {
            //System.out.println(classFile.getAbsolutePath());
            CompilationUnit ast= JavaParser.parse(classFile);
            List<MethodDeclaration> methods = ast.getNodesByType(MethodDeclaration.class);
            for (Node method : methods){

                MarkerAnnotationExpr override = new MarkerAnnotationExpr(new Name("Override"));
               // System.out.println(method.getChildNodes().get(0) instanceof MarkerAnnotationExpr);
                System.out.println(method.getChildNodes().contains(override));

                //System.out.println(method.getNodesByType(MarkerAnnotationExpr.class).contains(override));
                //System.out.println(new MarkerAnnotationExpr(new Name("truc")));
                //getChildNodes().contains(voidType));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){


            File destinationFolder = new File("C:/Users/sarah/Documents/MEASUREimpl/Measures/ClassComplexityInheritance/src/main/java/org/measure/impl");
            classCheckbis(destinationFolder);


    }*/
}
