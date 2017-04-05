package test;

import org.junit.Test;
import org.measure.cognitivecomplexitymeasure.CognitiveComplexity;
import org.measure.smm.measure.api.IMeasurement;

public class TestCognitiveComplexity {
	
	@Test
	public void testSVNCommitCount(){
		CognitiveComplexity measure = new CognitiveComplexity();
		measure.getProperties().put("Property1", "Value1");

			

		try {
			for(IMeasurement result : measure.getMeasurement()){
				System.out.println("Test Resul : " + result.getLabel());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
