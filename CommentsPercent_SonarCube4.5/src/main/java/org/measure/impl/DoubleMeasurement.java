package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8150d16a-366f-485b-b88b-18a2ccb0bb85")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("77af1c5c-e3d7-4db9-a9e7-734d676e626e")
    public DoubleMeasurement() {
        
    }

    @objid ("e65f9910-3ea9-4c5a-ae78-e591647c2610")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("793cd704-62bc-498e-a2d2-d141de37f3b6")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("3aca87d2-cdf3-461c-a01d-c59f2c5aecd4")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
