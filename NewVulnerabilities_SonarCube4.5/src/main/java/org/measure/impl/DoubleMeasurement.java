package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("14b51fea-4352-4a08-a595-311928889539")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("7452ffe1-091d-472f-9b83-b50e4f55b519")
    public DoubleMeasurement() {
        
    }

    @objid ("eb56a8b1-ec72-44bb-86b7-edf6eea1616a")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("6f98eb51-cfb3-4204-b8f2-f3eefe0a3073")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("aa2780ed-1e99-4f28-a998-2e7682354990")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
