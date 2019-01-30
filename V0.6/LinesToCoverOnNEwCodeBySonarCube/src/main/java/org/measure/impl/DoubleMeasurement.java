package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("27d58841-49cb-4986-85b5-3c22f6f176ea")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("83b3e931-1d95-4ca0-929e-e5c4201a73a2")
    public DoubleMeasurement() {
        
    }

    @objid ("912952de-d2fd-44d0-96a4-d4d08ec0f55a")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("0cbf1d6b-43d0-4c83-855d-fae048eecfb8")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("352ef500-5a29-4a6f-9314-9b4c830ffaaa")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
