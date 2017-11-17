package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("319fa8b0-8a60-4d4e-b504-e751719485f5")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("1855a59a-9fe5-495a-b091-2aeb9629e82a")
    public DoubleMeasurement() {
        
    }

    @objid ("5d6dca08-e345-41ec-979a-763d8c4b0275")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("7053181b-5b50-4cfd-9f40-26518e6ce580")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("8b0f6940-b472-44d6-97f4-b97ff509cd65")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
