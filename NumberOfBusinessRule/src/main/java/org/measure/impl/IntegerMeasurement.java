package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("93ece5cd-ec36-4c3f-8893-7bdb541fe6dd")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("aea46180-6aa9-4974-8ef1-4123b5fcded7")
    public IntegerMeasurement() {
        
    }

    @objid ("9e9d3b19-ad7a-4951-883c-cfa1cb3a2c7d")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("7aa7dc2b-2f4e-487e-b429-8207045c77ae")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("4a0b31f6-fb2b-4770-acae-35c0b9be8714")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
