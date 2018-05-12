package org.measure.power;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class PowerMeasure extends DirectMeasure {

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
			client.setUp();
		}
		return client;
	}
	
	public PowerMeasure() {
		super();
	}
	
	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		EmitClient client = this.getClient();
		String topic = this.getTopic();
		Long issued = this.getIssued();
		List<PowerMessage> messages = client.getMessages(topic, issued);
		List<IMeasurement> measurements = new ArrayList<IMeasurement>(messages.size());
		for (PowerMessage message : messages) {
			PowerMeasurement measurement = new PowerMeasurement(message);
			measurements.add(measurement);
		}
		return measurements;
	}

}