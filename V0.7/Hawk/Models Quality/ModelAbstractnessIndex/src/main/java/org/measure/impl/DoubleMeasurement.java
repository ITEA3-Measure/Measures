package org.measure.impl;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DoubleMeasurement extends DefaultMeasurement {
    public DoubleMeasurement() {
        
    }

    public void setValue(Double value) {
        addValue("value",value);
    }

    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
