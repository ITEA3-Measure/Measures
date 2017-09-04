package org.measure.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc2.*;

@objid ("ddaed34d-c390-4c8d-90ca-dc81aaf7e0f2")
public class WeightedClassComplexity extends DirectMeasure {
    @objid ("210d2ed7-a6cc-4259-84a4-e17b54d9de09")
     int weight;

    @objid ("8f5df965-8ab1-49bb-8786-6c729bd36cb2")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result=new ArrayList<IMeasurement>();
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
        deleteDir(destinationFolder);
        IntegerMeasurement weightmeasured=new IntegerMeasurement();
        weightmeasured.setValue(weight);
        result.add(weightmeasured);
        return result;
    }

    @objid ("0d3f91c6-7bec-4d29-957a-9f737589369a")
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

    @objid ("e72d64ae-b21e-471a-a94c-ada8407299be")
    public void fileParse(File classFile) {
        try {
            //System.out.println(classFile.getAbsolutePath());
            CompilationUnit ast= JavaParser.parse(classFile);
            List<FieldDeclaration> fields = ast.getNodesByType(FieldDeclaration.class);
            countFieldType(fields);
            /*for (Node block : field.getChildNodes()) {
                    if (block instanceof BlockStmt) {
                        countIfForStmt(block, 0);
                    }
                }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void countFieldType(List<FieldDeclaration> fields){
        for (Node field : fields) {
            if(field instanceof PrimitiveType){
                System.out.println("1" + field);
                weight+=1;
            }else if(field instanceof ArrayType){
                System.out.println("2" + field);
                weight+=2;

            }else if(field instanceof ClassOrInterfaceType){
                System.out.println("3" + field);
                weight+=3;
            }else{
                System.out.println("4" + field);
                weight+=4;
            }
        }
    }
    /*@objid ("685d8539-0c88-43c2-ad8c-66f4219de2c0")
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
    }*/

    @objid ("9148c04a-3359-4a39-a712-6766da7ffd39")
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


}

