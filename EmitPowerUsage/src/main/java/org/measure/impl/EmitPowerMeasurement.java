package org.measure.impl;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class EmitPowerMeasurement extends DefaultMeasurement {

	private static final String TOPIC = "topic";
	
	private static final String STAMP = "timestamp";
	
	private static final String VALUE = "value";

	public EmitPowerMeasurement(EmitPowerMessage message) {
		super();
		this.addValue(TOPIC, message.getTopic());
		this.addValue(STAMP, message.getIssued());
		this.addValue(VALUE, message.getValue());
	}

}
