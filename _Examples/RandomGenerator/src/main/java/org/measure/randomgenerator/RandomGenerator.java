package org.measure.randomgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;


public class RandomGenerator extends DirectMeasure {

	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> result = new ArrayList<>();
		
		int maxRange =  Integer.valueOf(getProperty("MaxRange"));
		int minRange = Integer.valueOf(getProperty("MinRange"));
		int delta = Integer.valueOf(getProperty("Delta"));
		int previousValue = Integer.valueOf(getProperty("PreviousValue"));

		Random gen = new Random();
		int value = gen.nextInt(delta * 2) - delta + previousValue;
		
		if(value < minRange){
			value = minRange;
		}
		
		if(value > maxRange){
			value = maxRange;
		}

		Measurement measurement = new Measurement();
		measurement.setValue(new Double(value));		
		result.add(measurement);
		
		getProperties().put("PreviousValue", String.valueOf(value));
				
		return result;
	}

}
