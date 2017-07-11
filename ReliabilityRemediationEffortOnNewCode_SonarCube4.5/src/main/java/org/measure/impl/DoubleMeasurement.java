package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("305bae62-432a-41cb-bd75-c3f104a8e78c")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("e3c13a44-4f96-4e00-a45b-cda547266100")
    public DoubleMeasurement() {
        
    }

    @objid ("4832a0d7-b626-4b12-b863-c348dfe2c881")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("b89843b6-4e54-4e73-9208-7e7b2d1ef018")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("5cce0ec7-78ba-4f34-b250-c0a2ecf8bd8a")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
