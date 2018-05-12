package org.measure.power;

import java.util.HashMap;
import java.util.Map;

import org.measure.smm.measure.api.IMeasurement;

public class EmitPowerMeasurement implements IMeasurement  {

	private static final String TOPIC = "topic";
	
	private static final String ISSUED = "issued";
	
	private static final String VALUE = "value";
	
	private Map<String, Object> values;
	
	private String topic;
	
	private Long issued;
	
	private Double value;
	
	private void setValues() {
		this.values = new HashMap<String, Object>(3);
	}
	
	public String getTopic() {
		return topic;
	}

	private void setTopic(String topic) {
		this.topic = topic;
		this.values.put(TOPIC, topic);
	}

	public Long getIssued() {
		return issued;
	}

	private void setIssued(Long issued) {
		this.issued = issued;
		this.values.put(ISSUED, issued);
	}

	public Double getValue() {
		return value;
	}

	private void setValue(Double value) {
		this.value = value;
		this.values.put(VALUE, value);
	}

	public EmitPowerMeasurement(EmitPowerMessage message) {
		this.setValues();
		this.setTopic(message.getTopic());
		this.setIssued(message.getIssued());
		this.setValue(message.getValue());
	}
	
	@Override
	public String getLabel() {
		return "Emit";
	}

	@Override
	public Map<String, Object> getValues() {
		return values;
	}

}
