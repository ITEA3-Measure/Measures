package org.measure.cognitivecomplexitymeasure;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
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
            System.out.println(classFile.getAbsolutePath());
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
    public static void main(String[] args) {


        File destinationFolder= new File("projectTarget");
        String url="https://svn.softeam.fr/svn/MEASURE/trunk/Software/SMMDesigner";
        try {
            final SVNURL svnurl = SVNURL.parseURIEncoded(url);
            System.out.println(svnurl);

        final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
        System.out.println(svnOperationFactory);
        BasicAuthenticationManager basicAuthenticationManager = new BasicAuthenticationManager("sdahab", "3bI2RE78m&");
        svnOperationFactory.setAuthenticationManager(basicAuthenticationManager);
        System.out.println(svnOperationFactory);
        SVNUpdateClient svnUpdateClient = new SVNUpdateClient(basicAuthenticationManager,null);
        svnUpdateClient.doCheckout(svnurl,destinationFolder,null,null,true);


        System.out.println(destinationFolder.exists());

       // System.out.println(checkout);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        //destinationFolder = svnOperationFactory.createCheckout().getSource().getFile();

        //System.out.println(destinationFolder);


    }*/

}
