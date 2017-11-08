package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("6279ad60-7a7a-4c06-a22e-50575fc4773c")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("7e241d67-125f-4283-ad8b-ff7ccbd848a2")
    public DoubleMeasurement() {
        
    }

    @objid ("218f7a49-d8ae-410d-80d0-23ed5fc57762")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("f316f7a3-f0db-43c1-babc-183e61ecf59e")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("21d5c7a2-a0fd-4301-b77c-da2160337b77")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
