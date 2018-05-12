package org.measure.impl;

public class EmitPowerMessage {

	private String topic;
	
	private Long issued;
	
	private Double value;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Long getIssued() {
		return issued;
	}

	public void setIssued(Long issued) {
		this.issued = issued;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
}
