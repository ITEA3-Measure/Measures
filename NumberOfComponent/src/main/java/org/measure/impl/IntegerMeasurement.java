package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("ee323cb5-e986-44b8-bb21-f9a9d197d1eb")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("5da93400-eeca-44e3-86c4-7df4eac2a71c")
    public IntegerMeasurement() {
        
    }

    @objid ("dc5f53ac-3173-4f11-a7dd-6d656e925f2a")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("3e166003-ef17-4c1f-8ad4-f682995e5ce8")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("22086564-48e0-43c1-94fb-0c8e84afe3a8")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
