package org.measure.randomgenerator;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class Measurement extends DefaultMeasurement {
	
	public void setValue(Double value){
		addValue("myValue",value);
	}
	
	public Double getValue(){
		return (Double) getValues().get("myValue");
	}
	
	@Override
	public String getLabel() {
		return getValues().get("myValue").toString();
	}

}
