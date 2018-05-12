package org.measure.power;

import org.junit.Test;
import org.measure.smm.measure.api.IMeasurement;

public class PowerMeasureTest {
	
	@Test
	public void test() throws Exception {
		PowerMeasure measure = new PowerMeasure();
		measure.getProperties().put("hostname", "http://emit.icam.fr/emit");
		measure.getProperties().put("port", "80");
		measure.getProperties().put("protocol", "http");
		measure.getProperties().put("username", "consortium@measure.org");
		measure.getProperties().put("password", "itea3measure");
		for(IMeasurement measurement : measure.getMeasurement()){
			System.out.println("Test Resul : " + measurement.getLabel());
		}
	}

}
