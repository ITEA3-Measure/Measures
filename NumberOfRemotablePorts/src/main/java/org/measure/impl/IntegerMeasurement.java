package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("25c94e2f-4885-4966-b375-c4f6cea02b20")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("9cbc6389-5275-44c8-9b47-9e4810b58f4e")
    public IntegerMeasurement() {
        
    }

    @objid ("44ac5e4e-d95b-4cee-ab2a-6b96b83b2711")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("fb3f1eec-ea72-4ad7-b2e7-d69f2c104cf8")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("0ca12495-ee0c-452f-b921-15bb10ca8f2c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
