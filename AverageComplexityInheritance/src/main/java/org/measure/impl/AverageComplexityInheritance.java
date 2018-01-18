package org.measure.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class AverageComplexityInheritance extends DerivedMeasure {

    int classNumber;
    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();


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

        classNumber =0;
        classCheck(destinationFolder);
        deleteDir(destinationFolder);

        //recupération de la mesure dépendante
        List<IMeasurement> cci = getMeasureInputByRole("ClassComplexityInheritance A");

        //resultat
        int averageWeight= (Integer)cci.get(0).getValues().get("value")/classNumber;

        //intégration du résultat final
        IntegerMeasurement weightmeasured=new IntegerMeasurement();
        weightmeasured.setValue(averageWeight);

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
            countNumberClasses(clas);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void countNumberClasses(List<ClassOrInterfaceDeclaration> clas) {
        if(clas.size()>0) {
            if(! clas.get(0).isInterface()){
                classNumber++;
            }
        }

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
            List<ClassOrInterfaceDeclaration> clas = ast.getNodesByType(ClassOrInterfaceDeclaration.class);
            if(clas.size()>0) {
                if(! clas.get(0).isInterface()){
                    System.out.println(1);
                }
                    //classNumber++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void  main (String args[]){
        File destinationFolder = new File("C:/Users/sarah/Documents/MEASUREimpl/Measures/ClassComplexity/src/main/java/org/measure/impl");
        classCheckbis(destinationFolder);
    }

*/


}
