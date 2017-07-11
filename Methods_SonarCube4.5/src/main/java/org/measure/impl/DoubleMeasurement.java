package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("44d0bf4d-61a1-42cd-b34e-dd1a79f64875")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("26c03ad0-8eef-4ff6-880d-c9109cc98d3e")
    public DoubleMeasurement() {
        
    }

    @objid ("8ff6e083-5ce3-44e9-ba8b-f1fca8c817e2")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("a3d65c2d-3b2e-4c9c-a6cf-3261d794326d")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("13c11296-5153-4728-a6c2-77e784dc5af2")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
