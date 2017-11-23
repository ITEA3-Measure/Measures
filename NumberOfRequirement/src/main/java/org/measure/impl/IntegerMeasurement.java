package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("104e910d-1eff-4168-b080-51c8a77a0cd5")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("7df748a5-a916-4f54-8716-c670fb0b4cd9")
    public IntegerMeasurement() {
        
    }

    @objid ("d70a3291-78c8-4ea2-b701-80a1c3c14b8d")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("911a45f0-3628-4c81-95df-4fb55c202e05")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("72c5498d-706f-4924-b3cd-2fb83f3b6618")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
