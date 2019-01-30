package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("0168ff95-1d99-4fb9-9956-03d105802c40")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("3d957141-dd9c-49f1-af47-1682c7051c4c")
    public DoubleMeasurement() {
        
    }

    @objid ("e4a071cb-3a14-4dc5-8eb6-b94fb0d72c96")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("225d7253-1b90-48af-ae9a-50266c820caa")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("8a6474cf-4233-4357-b3d8-16b7944fbc6a")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
