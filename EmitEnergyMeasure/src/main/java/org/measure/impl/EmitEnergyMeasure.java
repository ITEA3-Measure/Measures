package org.measure.impl;

import java.util.ArrayList;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;

public class EmitEnergyMeasure extends DerivedMeasure {

	@Override
	public List<IMeasurement> calculateMeasurement() throws Exception {
		String topic = this.getProperty("topic");
        Long started = Long.valueOf(this.getProperty("started"));
        Long stopped = Long.valueOf(this.getProperty("stopped"));
		Float value = this.doCompute(topic, started, stopped);
		EmitEnergyMeasurement measurement = new EmitEnergyMeasurement(topic, started, stopped, value);       
		List<IMeasurement> measurements = new ArrayList<IMeasurement>(1);
		measurements.add(measurement);
		return measurements;
	}

	private Float doCompute(String topic, Long started, Long stopped) {
		Float value = 0.0f;
		Float f_a = null;
        Long a = null;
        List<IMeasurement> measurements = this.getMeasureInputByRole("inputs");
        List<IMeasurement> inputs = this.doFilter(topic, started, stopped, measurements);
        for (IMeasurement input : inputs) {
        	if (f_a == null) {
        		f_a = (Float) input.getValues().get("value");
        		a = (Long) input.getValues().get("issued");
        	} else {
        		Float f_b = (Float) input.getValues().get("value");
        		Long b = (Long) input.getValues().get("issued");
        		value += (b - a) * ((f_a + f_b) / 2); 
        	}
        }
		return value;
	}

	private List<IMeasurement> doFilter(String filter, Long started, Long stopped, List<IMeasurement> inputs) {
		List<IMeasurement> outputs = new ArrayList<IMeasurement>(inputs.size());
		for (IMeasurement input : inputs) {
			try {
				String topic = (String) input.getValues().get("topic");
				Long issued = (Long) input.getValues().get("issued");
				if (topic.equals(topic) && started <= issued && issued <= stopped) {
					outputs.add(input);
				}
			} catch (Exception e) { }
		}
		return outputs;
	}

}
