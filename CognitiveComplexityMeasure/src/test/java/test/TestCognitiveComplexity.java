package test;

import org.junit.Test;
import org.measure.cognitivecomplexitymeasure.CognitiveComplexity;
import org.measure.smm.measure.api.IMeasurement;

public class TestCognitiveComplexity {
	
	@Test
	public void testCognitiveComplexity(){
		CognitiveComplexity measure = new CognitiveComplexity();
		measure.getProperties().put("URL", "C:/Users/Sdahab/Documents/MeasureImpl/Measures/projectmine");
		System.out.println(measure.getProperties());
			

		try {
			for(IMeasurement result : measure.getMeasurement()){
				System.out.println("Test Result : " + result.getLabel());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
