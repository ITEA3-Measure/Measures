package org.measure.power;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.icam.emit.entities.Measurement;

public class MeasurementFindTest {
	
	private static final String URL = "http://emit.icam.fr:8080/emit";
		
    @Before
    public final void setUp() { }

    @After
    public final void tearDown() { }
	
	@Test
	public void test() throws Exception {
		MeasurementFind app = new MeasurementFind(URL);
		List<Measurement> measurements = app.doGet("253f4097-de3e-40bf-b675-1dd70226f64b"); // FIXME 
		Assert.assertNotNull(measurements);
		Assert.assertTrue(measurements.size() > 0);
		for (Measurement measurement : measurements) {
			Assert.assertNotNull(measurement);
			Assert.assertNotNull(measurement.getFeature());
			Assert.assertNotNull(measurement.getFeature().getMeasure());
			Assert.assertNotNull(measurement.getFeature().getInstrument());
			Assert.assertEquals("power", measurement.getFeature().getMeasure().getName());
		}
	}
	
	public static void main(String[] arguments) throws Exception {
		MeasurementFindTest test = new MeasurementFindTest();
		test.setUp();
		test.test();
		test.tearDown();
	}

}
