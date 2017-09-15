package org.measure.power;

import org.junit.Test;
import org.measure.smm.measure.api.IMeasurement;

public class PowerMeasureTest {
	
	@Test
	public void test() throws Exception {
		PowerUsage measure = new PowerUsage();
		measure.getProperties().put("EmitServerUri", "http://emit.icam.fr:8080/emit");
		measure.getProperties().put("AccessToken", "253f4097-de3e-40bf-b675-1dd70226f64b"); // FIXME
		for(IMeasurement measurement : measure.getMeasurement()){
			System.out.println("Test Resul : " + measurement.getLabel());
		}
	}

}
