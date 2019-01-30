package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("4e7fe8a0-5b52-44d6-a5a6-b57ee1d73334")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("80e56e08-787c-4c4e-b076-cb0ed31de05f")
    public DoubleMeasurement() {
        
    }

    @objid ("e6e67265-7769-4d9e-8dcd-d35c5210d8e9")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("f9c5af17-b7da-4645-949b-8d4185af5663")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("e2e7850d-f560-4ca3-be59-e3d74ef8bcc5")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
