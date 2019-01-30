package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("e565bbb8-3d1d-4147-a4be-ba3fdc951c90")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("30ea4f6d-e14f-43b7-9c18-e0404fd4362b")
    public IntegerMeasurement() {
        
    }

    @objid ("a8d0ee49-730c-4407-8520-cbf1dfc10ae9")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("c4ce6a0c-674f-43f8-b05d-d6ea11803e0e")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("f750a591-9c47-45ef-9fc3-d62ed2dff5ab")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
