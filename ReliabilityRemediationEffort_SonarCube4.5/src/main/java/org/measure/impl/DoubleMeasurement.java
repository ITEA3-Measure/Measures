package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("6279ad60-7a7a-4c06-a22e-50575fc4773c")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("ae027e88-c25d-4e45-b090-00a2ee2a5db5")
    public DoubleMeasurement() {
        
    }

    @objid ("85164341-f22a-4505-84ca-6b4a731c4144")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("de8fc9fa-d7e1-4fa6-b3ff-f6676dcd640b")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("3bb0db27-9b2b-46f2-925c-bb6e7195a25d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
