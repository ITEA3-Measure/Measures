package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("f582fa6b-4d5c-4726-8b7e-274726058f13")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("46b88901-f46e-416f-85bb-2445263fc2b2")
    public DoubleMeasurement() {
        
    }

    @objid ("769a2f1a-3241-4d26-b569-4914b5c2fd2f")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("ac3fb3a0-777c-4809-8af8-b1b78bd4eafa")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("02746d0f-4dcc-4da7-a64d-20195acf0f97")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
