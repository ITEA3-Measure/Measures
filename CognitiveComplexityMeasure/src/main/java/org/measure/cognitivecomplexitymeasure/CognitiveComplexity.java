package org.measure.cognitivecomplexitymeasure;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.resolution.SymbolSolver;
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
        classCheck(files);
		return result;
	}

    private int classCheck(File files) {
        if (files.isDirectory()) {
            for (File child : files.listFiles()) {
                if (child.isFile() & child.getName().endsWith(".java")) {
                    weight+=fileParse(child);
                } else {
                    classCheck(child);
                }
            }

        }else if(files.isFile() & files.getName().endsWith(".java")){
            weight+=fileParse(files);
        }
        return weight;
    }

	public int fileParse(File classFile){
        int classweight=0;
        try {
            CompilationUnit ast= JavaParser.parse(classFile);
            ast.getNodeLists();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args){
        File files= new File("C:/Users/dahab_sa/Documents/MeasureIMPL/Measures/CognitiveComplexityMeasure/src/main/java/org/measure/cognitivecomplexitymeasure/CognitiveComplexity");
        try {
            CompilationUnit ast= JavaParser.parse(files);
            System.out.println(ast);
            System.out.println(ast.getNodeLists());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }


    }

}
