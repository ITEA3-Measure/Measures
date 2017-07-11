package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("b6ac2679-f83a-4b89-8248-126371c7f87c")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("4b4bfb86-2523-46ba-bfc4-48c4cf37fc60")
    public DoubleMeasurement() {
        
    }

    @objid ("83473aa5-9431-4e72-bb0e-5bd28548d7f2")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("1cc318e7-852c-47d4-8402-0e00182b8015")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("7e83aab6-4c57-4d2d-a9dd-f2526984d30c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
