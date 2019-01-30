package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("b09e4f83-0371-4b22-8ecc-ed770ebed01f")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("0d42d642-06fa-44dd-8477-c16f01c4c97c")
    public DoubleMeasurement() {
        
    }

    @objid ("a3bee996-e95f-4248-9fe1-31d4eef642b9")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("89c5ebd8-ebcc-4991-8e69-d1f4092be87b")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("414144fc-3d9a-4766-a1d3-d6782f7a7233")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
