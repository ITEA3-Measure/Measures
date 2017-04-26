package test;

import org.junit.Test;
import org.measure.cpuusagemeasure.CpuUsage;
import org.measure.smm.measure.api.IMeasurement;



public class TestCpuUsage {
	
	@Test
	public void testCpuUsage(){
		CpuUsage measure = new CpuUsage();
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
