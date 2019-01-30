package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("c6e972dc-12b3-40b5-8f1b-87c7a9b536fb")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("7fc85862-7587-4dd9-bd9c-3d46455b7b26")
    public DoubleMeasurement() {
        
    }

    @objid ("5195e7e2-6628-4333-816b-b08a7e6a2773")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("cfe03ade-2f25-46ed-9bc7-94c647906b2b")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("f1232f38-81e2-429d-859a-759d5f7e97db")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
