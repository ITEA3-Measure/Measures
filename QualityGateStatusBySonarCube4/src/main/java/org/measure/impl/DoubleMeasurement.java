package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("21dd273e-66ed-4045-b2eb-4f6d7da23ff6")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("0e1f7c48-0d77-457e-8c79-74cfecd6ee1c")
    public DoubleMeasurement() {
        
    }

    @objid ("ce0dd553-c7c6-45c9-86e6-47bba9fd1a8e")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("6c0f44fa-3e60-4305-bf9b-8fb1ca47f9f7")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("6f38e88f-6c06-4725-9dee-33da14d7e72a")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
