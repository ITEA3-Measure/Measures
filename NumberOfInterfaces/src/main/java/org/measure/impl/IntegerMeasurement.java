package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("eb5206ea-502f-4a6d-88e5-fffd5fe17963")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("26b20c1a-f5ed-4cb8-bea3-13a4ed051191")
    public IntegerMeasurement() {
        
    }

    @objid ("5d494301-7571-4c8d-bef0-a0ce9d413b1f")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("61ea9ca6-9310-4fdf-84d2-65c994f291a7")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("4f9059e8-ea7c-49de-9c51-e0373a88e298")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
