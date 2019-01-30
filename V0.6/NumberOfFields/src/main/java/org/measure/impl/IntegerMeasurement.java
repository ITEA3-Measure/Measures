package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("0a1a1089-2462-4568-a406-1a900a3cb441")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("ddfd46ba-7c99-4834-9a0b-efaef622e3a0")
    public IntegerMeasurement() {
        
    }

    @objid ("549d4834-e3cb-4fe3-8d72-5e81fbcd8c0e")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("71c44885-ef71-43bf-ad24-140b3a993366")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("616ea795-4be6-4382-a5da-be9f1b798098")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
