package org.measure.impl;

import java.util.ArrayList;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;

public class EmitEnergyMeasure extends DerivedMeasure {

	@Override
	public List<IMeasurement> calculateMeasurement() throws Exception {
		Double value = this.doCompute();
		String topic = this.getProperty("topic");
        Long started = Long.valueOf(this.getProperty("started"));
        Long stopped = Long.valueOf(this.getProperty("stopped"));
		EmitEnergyMeasurement measurement = new EmitEnergyMeasurement(topic, started, stopped, value);       
		List<IMeasurement> measurements = new ArrayList<IMeasurement>(1);
		measurements.add(measurement);
		return measurements;
	}

	private Double doCompute() {
		Double value = 0.0;
        Double f_a = null;
        Long a = null;
        List<IMeasurement> inputs = this.getMeasureInputByRole("inputs");
        for (IMeasurement input : inputs) {
        	if (f_a == null) {
        		f_a = (Double) input.getValues().get("value");
        		a = (Long) input.getValues().get("issued");
        	} else {
        		Double f_b = (Double) input.getValues().get("value");
        		Long b = (Long) input.getValues().get("issued");
        		value += (b - a) * ((f_a + f_b) / 2); 
        	}
        }
		return value;
	}

}
