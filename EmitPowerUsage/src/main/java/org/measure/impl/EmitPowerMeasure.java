package org.measure.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class EmitPowerMeasure extends DirectMeasure {

	private String topic;
	
	private Long last;
	
	private EmitClient client;

	private String getTopic() {
		if (topic == null) {
			topic = this.getProperty("topic");			
		}
		return topic;
	}

	private Long getIssued() {
		if (last == null) {
			last = Calendar.getInstance().getTimeInMillis();
		}
		return last;
	}
	
	private EmitClient getClient() throws Exception {
		if (client == null) {
			String hostname = this.getProperty("hostname");
			Integer port = Integer.valueOf(this.getProperty("port"));
			String protocol = this.getProperty("protocol");
			String toolname = this.getProperty("toolname");
			String username = this.getProperty("username");
			String password = this.getProperty("password");
			client = new EmitClient(protocol, hostname, port.intValue(), toolname, username, password);
		}
		return client;
	}
	
	public EmitPowerMeasure() {
		super();
	}
	
	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		EmitClient client = this.getClient();
		client.setUp();
		String topic = this.getTopic();
		Long issued = this.getIssued();
		List<EmitPowerMessage> messages = client.getMessages(topic, issued);
		client.tearDown();
		List<IMeasurement> measurements = new ArrayList<IMeasurement>(messages.size());
		for (EmitPowerMessage message : messages) {
			EmitPowerMeasurement measurement = new EmitPowerMeasurement(message);
			measurements.add(measurement);
		}
		return measurements;
	}

}
