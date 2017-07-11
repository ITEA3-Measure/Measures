package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("75227134-d5e3-4e06-aff7-88ec3fc28ede")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("a5c92750-dbea-44a8-a3ec-881b5d8f2fa2")
    public DoubleMeasurement() {
        
    }

    @objid ("5dcdc38c-a261-4199-969b-757191e743da")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("68263569-8ef9-40e1-8948-9b6fd2c63e2f")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("e0138ff4-fc41-4b66-96a6-6fd0940c6abc")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
