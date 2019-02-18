package org.measure.impl;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

import fr.icam.emit.entities.Message;

public class EmitPowerMeasurement extends DefaultMeasurement {

	private static final String TOPIC = "topic";
	
	private static final String ISSUED = "issued";
	
	private static final String VALUE = "value";

	public EmitPowerMeasurement(Message message) {
		super();
		String data = new String(message.getPayload());
		float value = Float.parseFloat(data);
		this.addValue(TOPIC, message.getTopic());
		this.addValue(ISSUED, message.getIssued());
		this.addValue(VALUE, value);
	}

}
