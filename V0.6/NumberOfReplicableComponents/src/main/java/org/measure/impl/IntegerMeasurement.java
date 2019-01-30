package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("142174eb-8f0a-4ca4-9cbb-6b55a106d872")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("dca87e41-4cb5-46e2-9236-237ce869803c")
    public IntegerMeasurement() {
        
    }

    @objid ("5b79fc2f-d748-422c-b0a3-a39ee65613c8")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("c788c903-cbf8-4840-a3fd-a138424f57b0")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("60308ee7-1899-4909-b890-5328912702e4")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
