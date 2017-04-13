package org.measure.cognitivecomplexitymeasure;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CognitiveComplexity extends DirectMeasure{
    int weight;

	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> result=new ArrayList<IMeasurement>();
		String url=getProperty("URL");
        File files= new File(url);
        System.out.println(files);
        weight=0;
        classCheck(files);

        IntegerMeasurement weightmeasured=new IntegerMeasurement();
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

        }else if(files.isFile() & files.getName().endsWith(".java")){
            fileParse(files);
        }

    }

	public void fileParse(File classFile){

        try {

            CompilationUnit ast= JavaParser.parse(classFile);
            List<MethodDeclaration> methods = ast.getNodesByType(MethodDeclaration.class);
            for (Node method : methods) {
                for (Node block : method.getChildNodes()) {
                    if (block instanceof BlockStmt) {
                        countIfForStmt(block, 0);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void countIfForStmt(Node block, int level) {

	    List<Node> stmtNodes = block.getChildNodes();
	   // System.out.println("Block : \n"+stmtNodes+" FIN BLOCK");
       // System.out.println("level : "+level+"\n");
        weight+=level;
        System.out.println(weight);
	    if (!stmtNodes.isEmpty()){
            for (Node stmtNode : stmtNodes) {
                 if (stmtNode instanceof IfStmt ) {
                     int levelIf=++level;
                     countIfForStmt(((IfStmt) stmtNode).getThenStmt(), levelIf);
                     Optional<Statement> elseStatement=((IfStmt) stmtNode).getElseStmt();
                     if (elseStatement.isPresent()) {
                        // System.out.println("else statement :\n"+elseStatement.get()+" Fin ELSE");
                         countIfForStmt(elseStatement.get(), levelIf);
                     }

                 }else if(stmtNode instanceof ForStmt) {
                       // System.out.println("For Statement :\n"+((ForStmt) stmtNode).getBody());
                        countIfForStmt(((ForStmt) stmtNode).getBody(),++level);

                 }else if(stmtNode instanceof ForeachStmt) {
                     //System.out.println("Foreach Statement :\n" + ((ForeachStmt) stmtNode).getBody());
                     countIfForStmt(((ForeachStmt) stmtNode).getBody(), ++level);
                 }
            }
        }

    }


/*
    public static void main(String[] args){
        //Desktop.getDesktop.open( new File(" file path"));
        File files= new File("projectmine/CognitiveComplexity.java");
        try {
            int poids=0;
            CompilationUnit ast= JavaParser.parse(files);
            List<MethodDeclaration> methods = ast.getNodesByType(MethodDeclaration.class);
            for (Node method : methods) {
                for (Node block : method.getChildNodes()) {
                    if (block instanceof BlockStmt) {
                        poids += countIfForStmt(block, 0);
                        System.out.println("poids : \n"+ poids);
                    }
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }


    }*/

}
