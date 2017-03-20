package org.measure.randomsum;

import java.util.ArrayList;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;

public class RandomSumImpl extends DerivedMeasure {

	@Override
	public List<IMeasurement> calculateMeasurement() throws Exception {
		Integer result = 0;
		for (IMeasurement operande : getMeasureInputByRole("RandomNumber")) {
			try {
				result = result + (Integer) operande.getValues().get("value");
			} catch (NumberFormatException e) {
				System.out.println("Non Numeric Operande");
			}
		}

		IntegerMeasurement measurement = new IntegerMeasurement();
		measurement.setValue(result);

		List<IMeasurement> measurements = new ArrayList<>();
		measurements.add(measurement);
		return measurements;
	}

}
