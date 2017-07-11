package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("09e3412d-9a68-450d-a44a-22e5144fb47e")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("2fc17dbc-9bc7-4973-81f4-11c91e2f8583")
    public DoubleMeasurement() {
        
    }

    @objid ("56b6f155-e3f3-47bf-8238-a1d56cffcd51")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("232ad522-e72b-49a9-8220-8321b841d492")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("e3cdb7fc-6125-4e2d-bb9e-6eed5f3b2e3d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
