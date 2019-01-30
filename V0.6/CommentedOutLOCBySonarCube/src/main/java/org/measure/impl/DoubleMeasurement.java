package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("9652aad1-f08c-459a-ad85-e91fbf5bd6d1")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("ade5cb11-df75-4223-8d82-364b0d817b31")
    public DoubleMeasurement() {
        
    }

    @objid ("2b1f305e-ae45-48fb-a8c8-def60dec7e18")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("7d2a6be6-fe2c-4a51-9d5c-2bd180001859")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("6bf7f627-4ea9-44df-a311-39ffced09d27")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
