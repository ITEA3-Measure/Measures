package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("14b51fea-4352-4a08-a595-311928889539")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("0a412ef1-6a0d-43e4-aba3-d89469efe9cb")
    public DoubleMeasurement() {
        
    }

    @objid ("6e599599-adb4-4b01-89fa-0b14aac32604")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("79208cf1-05f3-40bb-b4dd-341c62a22922")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("cdff81ba-cc6e-4839-851d-38f234f64506")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
