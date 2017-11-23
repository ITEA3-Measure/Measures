package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("9fd2cc9e-68d0-4a1f-873a-23bc1b10c698")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("ddef96e4-ac14-4d83-9a04-4eee3c85918e")
    public IntegerMeasurement() {
        
    }

    @objid ("78c05e71-9d0c-42c1-94ca-90c4b1185635")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("8ad7adf7-94df-44b1-9fa9-5e864317ccb9")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("c7ffa5d1-3cc0-4622-923b-b2aec340ce06")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
