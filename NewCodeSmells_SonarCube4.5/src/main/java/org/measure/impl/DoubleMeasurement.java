package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("b26053a8-e669-4c71-89ea-3e2b8f1b8d14")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("71247fe7-203d-45d6-8b16-42efda4b05c7")
    public DoubleMeasurement() {
        
    }

    @objid ("f483e883-036a-4824-aa1e-5f44ab6ff967")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("c2afb843-6604-4068-adc7-73aaff3f5aae")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("10f0c896-13ba-4ee3-9222-aacefc69bc17")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
