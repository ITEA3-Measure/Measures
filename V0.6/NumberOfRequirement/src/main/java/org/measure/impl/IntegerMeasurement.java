package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("118ee5ea-3b01-43bd-aade-0bab5d07024d")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("df25579b-ca37-49f4-8fda-674cea8a2b6e")
    public IntegerMeasurement() {
        
    }

    @objid ("f41090ba-857e-47f9-951a-b9e3ed7c5332")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("2a112e59-5e56-4bd6-a5fc-c3969b700478")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("cda91457-22c2-4e08-9d64-1a590a550e74")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
