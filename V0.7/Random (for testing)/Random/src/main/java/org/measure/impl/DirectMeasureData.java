package org.measure.impl;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DirectMeasureData extends DefaultMeasurement {

    public DirectMeasureData() {
        
    }

    public void setValue(Integer value) {
        addValue("value",value);
    }

    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
