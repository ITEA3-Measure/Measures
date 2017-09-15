package org.measure.packager;

import java.util.HashMap;

import org.measure.randomgenerator.RandomGenerator;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;

public class Test {

	public static void main(String[] args) {
		RandomGenerator measure = new RandomGenerator();
		HashMap<String, String> properties = new HashMap<>();
		
		measure.getProperties().put("MinRange", "0");
		measure.getProperties().put("MaxRange", "100");
		measure.getProperties().put("Delta", "5");
		measure.getProperties().put("PreviousValue", "0");

		try {
			for(IMeasurement measurement : measure.getMeasurement()){
				System.out.println(measurement.getLabel());
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
