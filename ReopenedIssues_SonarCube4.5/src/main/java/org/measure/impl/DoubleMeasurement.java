package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("80408a33-f6a7-4657-89ea-42c506da0dcc")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("995d6992-5e61-48d1-a12a-51515a1fb6c8")
    public DoubleMeasurement() {
        
    }

    @objid ("00ccdb9d-231c-4d59-96ba-59f3fedbf59d")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("d5245aec-8e58-4b2e-9cae-ae324d218589")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("2f2e5d44-39c8-46c7-b0ed-e7d4f6a89fb0")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
