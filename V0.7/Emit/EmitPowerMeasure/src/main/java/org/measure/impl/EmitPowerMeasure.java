package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

import fr.icam.emit.clients.EmitClient;
import fr.icam.emit.entities.Client;
import fr.icam.emit.entities.Message;

public class EmitPowerMeasure extends DirectMeasure {

private String uuid;
	
	private Long last;
	
	private String getUUID() {
		if (uuid == null) {
			uuid = this.getProperty("uuid");			
		}
		return uuid;
	}

	private Long getIssued(EmitClient client) throws Exception {
		if (last == null) {
			last = client.getTimestamp();
		}
		return last;
	}
	
	private EmitClient getClient() throws Exception {
		String hostname = this.getProperty("hostname");
		Integer port = Integer.valueOf(this.getProperty("port"));
		String protocol = this.getProperty("protocol");
		String username = this.getProperty("username");
		String password = this.getProperty("password");
		return new EmitClient(protocol, hostname, port.intValue(), username, password);
	}
	
	public EmitPowerMeasure() {
		super();
	}
	
	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		EmitClient client = this.getClient();
		client.setUp();
		UUID uuid = UUID.fromString(this.getUUID());
		Long issued = this.getIssued(client);
		Client cl = client.getClient(uuid);
		List<Message> messages = client.getMessagePage(cl, issued);
		List<IMeasurement> measurements = new ArrayList<IMeasurement>(messages.size());
		for (Message message : messages) {
			EmitPowerMeasurement measurement = new EmitPowerMeasurement();
			measurement.update(message);
			measurements.add(measurement);
			if (last < message.getIssued()) {
				last = message.getIssued();
			}
		}
		client.tearDown();
		return measurements;
	}

}
