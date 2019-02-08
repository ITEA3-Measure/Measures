package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("719c0b5b-98aa-4ed9-a78e-f07ca6d18b33")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("59c5ce2c-a007-439b-837a-c0d91a6c7167")
    public IntegerMeasurement() {
        
    }

    @objid ("e7899c2a-55e7-48a5-9ced-1b5614a4b1d5")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("c9b7d024-ea04-4c0e-9012-ab320ce052d8")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("410c2235-727d-4d85-bfb4-bf77d5006438")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
