package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("7d72e47c-f27a-4445-b40a-b0cb9589e82b")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("acde152e-5ceb-48bf-b638-9b52ccf786bd")
    public DoubleMeasurement() {
        
    }

    @objid ("1219aeeb-819c-45cc-aef9-466c48172568")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("0b448794-9e04-4860-9b35-1bc0bea9fd79")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("fafbdbdf-13f5-47db-b7cf-52b1723305a2")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
