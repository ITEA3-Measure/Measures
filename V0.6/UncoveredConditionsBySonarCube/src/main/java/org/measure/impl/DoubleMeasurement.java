package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8016a5b3-6b2c-4386-8023-4c1c3beabdf2")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("d5b6bd69-9f66-41c6-98e6-7f194bc940be")
    public DoubleMeasurement() {
        
    }

    @objid ("9f94d7bc-08fb-4b34-9218-d2ba2d46812d")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("cdfe83d4-dda0-4772-b8af-56d436ba4f73")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("cb361cb0-0183-411e-998d-1b9526b5263a")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
