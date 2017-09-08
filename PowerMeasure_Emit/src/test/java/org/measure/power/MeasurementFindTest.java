package org.measure.power;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.icam.emit.clients.MeasurementFind;
import fr.icam.emit.entities.Measurement;

public class MeasurementFindTest {
	
	private static final String URL = "http://172.21.50.3:8080/emit";
	
	private DateFormat formatter;
	
    @Before
    public final void setUp() {
    	formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @After
    public final void tearDown() {
    	
    }
	
	@Test
	public void test() throws Exception {
		Date started = formatter.parse("2017-09-01 00:00:00");
		Date stopped = formatter.parse("2017-09-10 00:00:00");
		MeasurementFind app = new MeasurementFind(URL);
		List<Measurement> measurements = app.doGet("power", started, stopped);
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
