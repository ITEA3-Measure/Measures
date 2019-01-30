package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("ec9085ca-9bc5-441b-a8d8-fbd95ed41081")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("be8ef378-a9d3-4445-aabf-907985f44633")
    public IntegerMeasurement() {
        
    }

    @objid ("b0d72241-79b8-402c-9dab-59c9dce6e054")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("f0f4bb81-44fe-4cf0-a806-4c3bc505c508")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("403a8bc9-7a46-4988-bb58-72bea913ea8a")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
