package org.measure.power;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

import fr.icam.emit.clients.MeasurementFind;
import fr.icam.emit.entities.Measurement;

public class PowerMeasure extends DirectMeasure {

	private DateFormat formatter;

	
	public PowerMeasure() {
		super();
		this.formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> allMeasurements = new LinkedList<IMeasurement>();

		String emitServerUri = this.getProperty("EmitServerUri");
		String startDateTime = this.getProperty("StartDateTime");
		String endDateTime = this.getProperty("EndDateTime");
		
		URI uri = URI.create(emitServerUri);
		Date started = formatter.parse(startDateTime);
		Date stopped = formatter.parse(endDateTime);
		MeasurementFind app = new MeasurementFind(uri.toURL().toString());
		List<Measurement> items = app.doGet("power", started, stopped);
		for (Measurement item : items) {
			List<IMeasurement> measurements = this.getMeasurements(item);
			allMeasurements.addAll(measurements);
		}
		return allMeasurements;
	}

	private List<IMeasurement> getMeasurements(Measurement item) throws Exception {
		List<IMeasurement> measurements = new LinkedList<IMeasurement>();
		Map<Long, Double> data = item.getData();
		for (Long time : data.keySet()) {
			Double value = data.get(time);
			PowerMeasurement measurement = new PowerMeasurement();
			measurement.addValue("feature.id", item.getFeature().getId());
			measurement.addValue("feature.name", item.getFeature().getName());
			measurement.addValue("feature.factor", item.getFeature().getFactor());
			measurement.addValue("measure.id", item.getFeature().getMeasure().getId());
			measurement.addValue("measure.name", item.getFeature().getMeasure().getName());
			measurement.addValue("measure.unit", item.getFeature().getMeasure().getUnit());
			measurement.addValue("instrument.id", item.getFeature().getInstrument().getId());
			measurement.addValue("instrument.name", item.getFeature().getInstrument().getName());
			measurement.addValue("instrument.uri", item.getFeature().getInstrument().getUri());
			measurement.addValue("time", time);
			measurement.addValue("value", value);
			measurements.add(measurement);
		}
		return measurements;
	}

}
