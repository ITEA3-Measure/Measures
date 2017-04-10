package org.measure.cognitivecomplexitymeasure;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CognitiveComplexity extends DirectMeasure{
    int weight;

	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> result=new ArrayList<IMeasurement>();
		String url=getProperty("URL");
        File files= new File(url);
        weight=0;
        //classCheck(files);

		return result;//.add(weight);
	}
/*
    private void classCheck(File files) {
        //voir probleme de répertoire !!
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
                        countIfForStmt(block,1);

                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
*/
    public static int countIfForStmt(Node block, int level) {

	    List<Node> stmtNodes = block.getChildNodes();
	    int poid=level;
	    System.out.println(stmtNodes);
	    if (!stmtNodes.isEmpty()){
            for (Node stmtNode : stmtNodes) {

                System.out.println("first :\n"+stmtNode.getNodesByType(IfStmt.class));
                 if (stmtNode instanceof IfStmt || stmtNode instanceof ForStmt) {
                 //weight += level;
                     System.out.println("second :\n"+stmtNode.getChildNodes().get(1).getClass());
                     System.out.println("level :\n " + level);
                     poid += level;
                     countIfForStmt(stmtNode, level + 1);

                 }
            }
        }
        return poid;
    }



    public static void main(String[] args){
        //Desktop.getDesktop.open( new File(" file path"));
        File files= new File("projectmine/CognitiveComplexity.java");
        try {
            int poids=0;
            CompilationUnit ast= JavaParser.parse(files);
            List<MethodDeclaration> methods = ast.getNodesByType(MethodDeclaration.class);
            for (Node method : methods) {
                method.getChildNodes().forEach(c -> System.out.println("Method childs : \n"+c.getClass()+" FIN"));
                //System.out.println(method.getNodesByType(IfStmt.class));
                for (Node block : method.getChildNodes()) {
                   // block.getChildNodes().forEach(c -> System.out.println("Method childs childs : \n"+c.getClass()+" fin\n"));
                    if (block instanceof BlockStmt) {
                        block.getChildNodes().forEach(c -> System.out.println("block childs : \n"+c.getClass()+" FIN2"));
                        poids += countIfForStmt(block, 1);
                        System.out.println("poids : \n"+ poids);
                    }
                }
            }

          /*  for (BlockStmt stmt: stmts){
                List<IfStmt> ifstmts=stmt.getNodesByType(IfStmt.class);
                if(!ifstmts.isEmpty()){
                System.out.println(ifstmts);
                System.out.println("first level :\n"+stmt.getNodesByType(IfStmt.class));

                for (IfStmt ifstmt: ifstmts){
                    List<IfStmt> sousifstmts=ifstmt.getNodesByType(IfStmt.class);
                    if (!sousifstmts.isEmpty())
                        System.out.println("second level :\n"+ifstmt.getNodesByType(IfStmt.class));
                }

                }

            }*/

        /*    List<Node> nodes = ast.getChildNodes();
            for (Node nodechild : nodes){
                if(nodechild instanceof ClassOrInterfaceDeclaration) {
                    List<Node> nodechildlist = nodechild.getChildNodes();
                    for (Node sousnodechild: nodechildlist) {
                        if (sousnodechild instanceof MethodDeclaration) {
                            List<Node> methodNodes = sousnodechild.getChildNodes();
                            for (Node methodNode:methodNodes){
                                if (methodNode instanceof BlockStmt){
                                    List<Node> stmtNodes = methodNode.getChildNodes();
                                    for (Node stmtNode:stmtNodes) {
                                        System.out.println("voici l'ast :\n" + stmtNode.getClass());//.getBegin());
                                    }

                                }


                            }

                        }
                    }
                }
            }*/
           // System.out.println("voici node list :\n"+ast);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }


    }

}
