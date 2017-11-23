package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("9f243dc3-1675-4ad7-bc01-cfe0abf5f93e")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("2ef96fe4-c618-44b2-bb3d-d8a49e01e149")
    public IntegerMeasurement() {
        
    }

    @objid ("53055ed2-15f6-4788-8ae2-ba6bb46bf3e5")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("234c252a-864a-4399-9196-956f52aecfd3")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("27587428-3d66-4687-a281-23e46d85e29a")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
