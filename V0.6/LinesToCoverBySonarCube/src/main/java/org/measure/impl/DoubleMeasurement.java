package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("f0a15ee6-8f72-4a16-a6df-d4e2c080535d")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("e8f3f38d-7109-4094-9af6-fd75abb36e93")
    public DoubleMeasurement() {
        
    }

    @objid ("69137006-39b9-4d37-81c2-85bb3a66c92a")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("3f797761-23c9-4d27-8582-92d7defa6fa2")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("a3e5262e-4590-465b-9adf-901ba1fb9c61")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
