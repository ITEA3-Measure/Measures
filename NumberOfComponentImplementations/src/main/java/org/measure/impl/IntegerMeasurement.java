package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("b9413837-583e-4a00-b0d2-bdbf40b472f8")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("a7e2993f-fe55-48d7-b728-1d4cdc51c3b1")
    public IntegerMeasurement() {
        
    }

    @objid ("80eb4aba-4fcd-462f-8a88-ff808565f394")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("5daacdf7-b0a0-4961-9811-a26f22f67832")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("f65f83eb-8e29-4ce9-bd9a-a787180f9f3a")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
