package org.measure.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;


public class ClassComplexityInheritance extends DirectMeasure {

    int weight;
    String className;
    Map<String, Integer> inheritedclassNumber;
    Map<String, Integer> inheritedclassWeight;

    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        inheritedclassNumber = new HashMap<>();
        inheritedclassWeight = new HashMap<>();
        String url = getProperty("URL");
        String login = getProperty("LOGIN");
        String password = getProperty("PASSWORD");
        File destinationFolder = new File("projectTarget");
        try {
            final SVNURL svnurl = SVNURL.parseURIEncoded(url);
            System.out.println(svnurl);
            final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
            BasicAuthenticationManager basicAuthenticationManager = new BasicAuthenticationManager(login, password);
            svnOperationFactory.setAuthenticationManager(basicAuthenticationManager);
            SVNUpdateClient svnUpdateClient = new SVNUpdateClient(basicAuthenticationManager, null);
            svnUpdateClient.doCheckout(svnurl, destinationFolder, null, null, true);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        weight = 0;
        classCheck(destinationFolder);
        deleteDir(destinationFolder);
        weightInheritance();

        IntegerMeasurement weightmeasured = new IntegerMeasurement();
        weightmeasured.setValue(weight);
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
        } else if (files.isFile() & files.getName().endsWith(".java")) {
            fileParse(files);
        }
    }

    public void fileParse(File classFile) {
        try {
            System.out.println(classFile.getAbsolutePath());
            CompilationUnit ast = JavaParser.parse(classFile);
            
            List<ClassOrInterfaceDeclaration> clas = ast.getNodesByType(ClassOrInterfaceDeclaration.class);
            if(clas.size()>0){
                className=clas.get(0).getNameAsString();

                //check inheritance
                if (clas.get(0).getExtendedTypes().size()>0){
                    String name=clas.get(0).getExtendedTypes().get(0).getNameAsString();
                    if (inheritedclassNumber.containsKey(name)){
                        int number= inheritedclassNumber.get(name)+1;
                        inheritedclassNumber.put(name,number);
                    }else{
                        inheritedclassNumber.put(name,1);
                    }
                }
            }
            //System.out.println(clas.get(0).getExtendedTypes().get(0).getName());
            
            //compute class weight
            int classweight=weight;
            List<MethodDeclaration> methods = ast.getNodesByType(MethodDeclaration.class);
            for (Node method : methods) {
                for (Node block : method.getChildNodes()) {
                    if (block instanceof BlockStmt) {
                        countIfForStmt(block, 0);
                    }
                }
            }
            classweight=weight-classweight;
            if(clas.size()>0) {
                inheritedclassWeight.put(className, classweight);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void countIfForStmt(Node block, int level) {
        List<Node> stmtNodes = block.getChildNodes();
        // System.out.println("Block : \n"+stmtNodes+" FIN BLOCK");
        // System.out.println("level : "+level+"\n");
        weight += level;
        //System.out.println(weight);
        if (!stmtNodes.isEmpty()) {
            for (Node stmtNode : stmtNodes) {
                if (stmtNode instanceof IfStmt) {
                    int levelIf = ++level;
                    countIfForStmt(((IfStmt) stmtNode).getThenStmt(), levelIf);
                    Optional<Statement> elseStatement = ((IfStmt) stmtNode).getElseStmt();
                    if (elseStatement.isPresent()) {
                        // System.out.println("else statement :\n"+elseStatement.get()+" Fin ELSE");
                        countIfForStmt(elseStatement.get(), levelIf);
                    }
                } else if (stmtNode instanceof ForStmt) {
                    // System.out.println("For Statement :\n"+((ForStmt) stmtNode).getBody());
                    countIfForStmt(((ForStmt) stmtNode).getBody(), ++level);
                } else if (stmtNode instanceof ForeachStmt) {
                    //System.out.println("Foreach Statement :\n" + ((ForeachStmt) stmtNode).getBody());
                    countIfForStmt(((ForeachStmt) stmtNode).getBody(), ++level);
                }
            }
        }
    }

    public void weightInheritance() {
        for (Map.Entry<String, Integer> entry : inheritedclassNumber.entrySet()) {
            if(inheritedclassWeight.get(entry.getKey()) == null ){
                inheritedclassWeight.put(entry.getKey(),0);
            }
            int inheritedweight = entry.getValue() * inheritedclassWeight.get(entry.getKey());
            weight = weight + inheritedweight;
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
    public static void classcheckbis(File files) {
        if (files.isDirectory()) {
            for (File child : files.listFiles()) {
                if (child.isFile() & child.getName().endsWith(".java")) {
                    fileparsebis(child);
                } else {
                    classcheckbis(child);
                }
            }
        } else if (files.isFile() & files.getName().endsWith(".java")) {
            fileparsebis(files);
        }
    }

    public static void fileparsebis(File classFile) {
        try {
            System.out.println(classFile.getAbsolutePath());
            CompilationUnit ast = JavaParser.parse(classFile);
            List<ClassOrInterfaceDeclaration> clas = ast.getNodesByType(ClassOrInterfaceDeclaration.class);
            classNamebis=clas.get(0).getNameAsString();

            //check inheritance
            if (clas.get(0).getExtendedTypes().size()>0){
                String name=clas.get(0).getExtendedTypes().get(0).getNameAsString();
                if (inheritedclassNumberbis.containsKey(name)){
                    int number= inheritedclassNumberbis.get(name)+1;
                    inheritedclassNumberbis.put(name,number);
                }else{
                    inheritedclassNumberbis.put(name,1);
                }
            }
            //getExtendedTypes().get(0).getName().toString()
            int classweightbis=weightbis;
            List<MethodDeclaration> methods = ast.getNodesByType(MethodDeclaration.class);
            for (Node method : methods) {
                for (Node block : method.getChildNodes()) {
                    if (block instanceof BlockStmt) {
                        countIfForStmtbis(block, 0);
                    }
                }
            }
            classweightbis=weightbis-classweightbis;
            inheritedclassWeightbis.put(classNamebis,classweightbis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void countIfForStmtbis(Node block, int level) {
        List<Node> stmtNodes = block.getChildNodes();
        // System.out.println("Block : \n"+stmtNodes+" FIN BLOCK");
        // System.out.println("level : "+level+"\n");
        weightbis += level;
        //System.out.println(weight);
        if (!stmtNodes.isEmpty()) {
            for (Node stmtNode : stmtNodes) {
                if (stmtNode instanceof IfStmt) {
                    int levelIf = ++level;
                    countIfForStmtbis(((IfStmt) stmtNode).getThenStmt(), levelIf);
                    Optional<Statement> elseStatement = ((IfStmt) stmtNode).getElseStmt();
                    if (elseStatement.isPresent()) {
                        // System.out.println("else statement :\n"+elseStatement.get()+" Fin ELSE");
                        countIfForStmtbis(elseStatement.get(), levelIf);
                    }
                } else if (stmtNode instanceof ForStmt) {
                    // System.out.println("For Statement :\n"+((ForStmt) stmtNode).getBody());
                    countIfForStmtbis(((ForStmt) stmtNode).getBody(), ++level);
                } else if (stmtNode instanceof ForeachStmt) {
                    //System.out.println("Foreach Statement :\n" + ((ForeachStmt) stmtNode).getBody());
                    countIfForStmtbis(((ForeachStmt) stmtNode).getBody(), ++level);
                }
            }
        }
    }
    public static void main(String[] args) {

        File destinationFolder = new File("C:/Users/sarah/Documents/MEASUREimpl/Measures/ClassComplexityInheritance/src/main/java/org/measure/impl");
        classcheckbis(destinationFolder);
        System.out.println(inheritedclassWeightbis.size());
        System.out.println(inheritedclassNumberbis.size());
        System.out.println(weightbis);

            for (Map.Entry<String, Integer> entry : inheritedclassNumberbis.entrySet()) {
                if(inheritedclassWeightbis.get(entry.getKey()) == null ){
                    inheritedclassWeightbis.put(entry.getKey(),0);
                }
                System.out.println(entry.getKey() + ":" + entry.getValue());
                System.out.println(inheritedclassWeightbis.get(entry.getKey()));
                int inheritedweight = entry.getValue()*inheritedclassWeightbis.get(entry.getKey());

                weightbis = weightbis + inheritedweight;
            }


        System.out.println(weightbis);
    }*/
}


