package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("6ef7b8d4-d1b0-4c39-8591-5bb53db738b9")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("411ce5af-d667-43cd-8343-1b6099ebe31a")
    public IntegerMeasurement() {
        
    }

    @objid ("70468cc8-81c5-4a05-bfa3-b21eccabbc89")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("2a6ee580-8e58-48dd-ab1d-b49f0778832a")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("9a8970a4-4488-47f7-b120-6f31b72f26d4")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
