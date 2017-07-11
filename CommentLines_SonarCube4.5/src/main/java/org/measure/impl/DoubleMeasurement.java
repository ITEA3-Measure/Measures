package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("18306201-f16d-42c8-86af-f0727d3199c3")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("bede7fe7-ad73-4e37-9d9d-7c309fa8afa9")
    public DoubleMeasurement() {
        
    }

    @objid ("55528810-906b-4a74-a808-badcafb49656")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("fcd2c3e2-c594-4e41-9953-1a45a4415a97")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("0e80641c-1be4-4378-b221-e8baf5065e78")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
