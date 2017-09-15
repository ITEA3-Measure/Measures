package org.measure.power;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

import fr.icam.emit.entities.Measurement;

public class PowerUsage extends DirectMeasure {

	public PowerUsage() {
		super();
	}
	
	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> allMeasurements = new LinkedList<IMeasurement>();

		String emitServerUri = this.getProperty("EmitServerUri");
		String accessToken = this.getProperty("AccessToken");
		
		URI uri = URI.create(emitServerUri);
		EmitMeasurementRetrieve app = new EmitMeasurementRetrieve(uri.toURL().toString());
		List<Measurement> items = app.doGet(accessToken);
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
