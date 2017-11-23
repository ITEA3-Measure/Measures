package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("f2a528c2-0358-435a-b710-f189229504ba")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("cac252c6-9a41-40ad-974f-455459f68594")
    public IntegerMeasurement() {
        
    }

    @objid ("c1f460b7-c4cb-471c-b545-081aa8cdfb80")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("e62e1848-3b2d-4420-b894-eb6bc5ffbc42")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("a5ba7585-41e4-4771-90d2-e7bb1ab54670")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
