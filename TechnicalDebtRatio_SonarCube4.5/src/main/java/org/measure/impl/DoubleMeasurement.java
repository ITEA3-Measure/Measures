package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("5b8e593a-b79a-4d16-87e2-bbc5fdc68eab")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("ce2bb8c5-6430-4aa5-8f78-6f2b8a15bb39")
    public DoubleMeasurement() {
        
    }

    @objid ("3616544c-aa0d-4f75-b0bc-27b1cfc4991a")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("6e9b2b60-0c3c-4a9e-9b21-9d4877e25265")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("f0415b22-7dbf-43b6-b3fe-8a8fb8ceac5b")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
