package org.measure.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.measure.impl.EmitPowerMeasure;
import org.measure.smm.measure.api.IMeasurement;

public class EmitPowerMeasureTest extends EmitPowerMeasure {

	@Before
	public void setup() {
		this.getProperties().put("hostname", "app.icam.fr");
		this.getProperties().put("port", "80");
		this.getProperties().put("protocol", "http");
		this.getProperties().put("toolname", "emit");
		this.getProperties().put("username", "measure@emit.icam.fr");
		this.getProperties().put("password", "m3@suR");
		this.getProperties().put("topic", "peaktech/power");
	}
	
	@Test
	public void test() throws Exception {
		measurements();
		skip(3000);
		measurements();
	}

	private void skip(long duration) {
		long last = System.currentTimeMillis();
		System.out.println(last);
		do { } while (System.currentTimeMillis() - last < duration);
	}

	private List<IMeasurement> measurements() throws Exception {
		List<IMeasurement> measurements = this.getMeasurement();
		Assert.assertNotNull(measurements);
		Assert.assertTrue(measurements.size() >= 0);
		System.out.println(measurements.size());
		for (IMeasurement measurement : measurements) {
			System.out.println(measurement.getLabel());
		}
		return measurements;
	}
	
}
