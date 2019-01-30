package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("584c4973-bb05-41ed-817c-d7e986e24a0c")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("d2caffe3-51f9-47d2-a4bd-7543fdf7a74d")
    public DoubleMeasurement() {
        
    }

    @objid ("58550ddd-17b5-40e3-9f80-e14a05ad75bc")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("5fa1e086-4c07-4bc7-86ca-069a541021c3")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("178547a7-8dc0-4403-8195-e863b2192d30")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
