package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d8ed0494-90c5-4dd0-a880-3f957cc1ba12")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("43500163-0316-494d-93f7-fbfe56d6c2ab")
    public IntegerMeasurement() {
        
    }

    @objid ("4d725c0e-2f64-4bcc-b3ab-2edbdcdddf92")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("8d92dd59-c7dc-4cd2-999d-b44dfb2b4258")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("9bf3ab8a-3f21-486a-ae2c-284dd8b21556")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
