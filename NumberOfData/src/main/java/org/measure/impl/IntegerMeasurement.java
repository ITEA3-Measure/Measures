package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("10106c5f-9b29-44fa-a9af-0f9657184d8f")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("f42d2349-5383-4fbe-85c1-d67cbb4e0410")
    public IntegerMeasurement() {
        
    }

    @objid ("f6f46fc8-1096-417e-af60-62f1a7fbc3e3")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("40d8847c-c2bf-434b-bea7-4687e692a0f4")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("ff666880-75b9-4a6b-8790-9de2578d12fd")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
