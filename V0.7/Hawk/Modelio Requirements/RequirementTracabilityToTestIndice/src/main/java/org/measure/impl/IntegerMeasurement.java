package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("3eca9b75-9c49-4b67-8903-69f518278373")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("d9aafc71-f735-4d0c-a173-bc21ab7ed49d")
    public IntegerMeasurement() {
        
    }

    @objid ("4e734690-6a81-4d78-be6d-a4b535df61f6")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("56648b15-1fce-4bef-8dc7-3df69fda1289")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("ec2acf58-be1a-41e1-bea3-e444e8dcf9a4")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
