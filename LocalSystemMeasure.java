package org.measure.localsystemmeasure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.Date;

//import org.measure.localsystemmeasure.impl.SystemUtil;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

import org.measure.localsystemmeasure.impl.RabbitExchangeMonitor;

public class LocalSystemMeasure extends DirectMeasure {

	private RabbitExchangeMonitor rMon;
	private boolean working;
	private Date lastCall;
	
	public LocalSystemMeasure() {
		working = false;
		String host = getProperty("HOST");
		String username = getProperty("USERNAME");
		String password = getProperty("PASSWORD");
		String exchange = getProperty("EXCHANGE");
		
		try {
			rMon = new RabbitExchangeMonitor(host, username, password, exchange);
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lastCall = new Date();
		working = true;
	}
	
	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> result = new ArrayList<>();
		DefaultMeasurement measure = new DefaultMeasurement();
		
		Date nT = new Date();
		double rate = (double)rMon.GetMessageCount() / (nT.getTime() - lastCall.getTime());		
		measure.addValue("Rate", rate);
		result.add(measure);

		lastCall = nT;
		return result;
	}
	
	public static void main(String[] args) {
		
	}

}
