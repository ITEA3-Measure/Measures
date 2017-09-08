package org.measure.power;

import org.junit.Test;
import org.measure.smm.measure.api.IMeasurement;

public class PowerMeasureTest {
	
	@Test
	public void test() throws Exception {
		PowerMeasure measure = new PowerMeasure();
		measure.getProperties().put("EmitServerUri", "http://172.21.50.3:8080/emit");
		measure.getProperties().put("StartDateTime", "2017-09-01 00:00:00");
		measure.getProperties().put("EndDateTime", "2017-09-10 00:00:00");
		for(IMeasurement measurement : measure.getMeasurement()){
			System.out.println("Test Resul : " + measurement.getLabel());
		}
	}

}
