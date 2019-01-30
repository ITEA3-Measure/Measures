package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("cf88322d-f50d-4592-a6cc-75d1d1b1eb52")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("3137fdfd-165a-490d-be39-212196ab879b")
    public DoubleMeasurement() {
        
    }

    @objid ("b6c919fc-c508-4aff-baf9-f6e62cb77bd4")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("86d392c1-d18f-4d5a-9190-254a9fc359b0")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("4c142f30-e0c1-4f42-b4f3-3807ab273282")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
