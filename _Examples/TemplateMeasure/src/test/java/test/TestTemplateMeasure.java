package test;

import org.junit.Test;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.templatemeasure.TemplateMeasure;

public class TestTemplateMeasure {
	
	@Test
	public void testSVNCommitCount(){
		TemplateMeasure measure = new TemplateMeasure();
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
