package test;

import org.junit.Test;
import org.measure.memoryusagemeasure.MemoryUsage;
import org.measure.smm.measure.api.IMeasurement;



public class TestMemoryUsage {
	
	@Test
	public void testMemoryUsage(){
		MemoryUsage measure = new MemoryUsage();

		try {
			for(IMeasurement result : measure.getMeasurement()){
				System.out.println("Test Result : " + result.getLabel());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
