package org.measure.cognitivecomplexitymeasure;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CognitiveComplexity extends DirectMeasure{

	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> result=new ArrayList<IMeasurement>();
		String url=getProperty("URL");


		return result;
	}

	public void projectParser(String url){
        File project= new File(url);
/* pour chaque element du projet si c'est un fichier.java je le parse
    sinon je recommence sur les enfants :
    if (file.isDirectory()) {
            for (File child : file.listFiles()) {
explore(level + 1, path + "/" + child.getName(), child);
 */
        try {

            CompilationUnit javaParser= JavaParser.parse(project);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
