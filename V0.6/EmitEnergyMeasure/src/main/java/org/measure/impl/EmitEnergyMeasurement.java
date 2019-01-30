package org.measure.impl;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class EmitEnergyMeasurement extends DefaultMeasurement {

	private static final String TOPIC = "topic";
	
	private static final String STARTED = "started";
	
	private static final String STOPPED = "stopped";
	
	private static final String VALUE = "value";
	
	public EmitEnergyMeasurement(String topic, Long started, Long stopped, Float value) {
		super();
		this.addValue(TOPIC, topic);
		this.addValue(STARTED, started);
		this.addValue(STOPPED, stopped);
		this.addValue(VALUE, value.floatValue());
	}

}
