package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("576ebe2c-435e-4107-aa91-e4e7180613d9")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("79d151c1-0a49-4601-83a8-f744be70a037")
    public IntegerMeasurement() {
        
    }

    @objid ("5eeaa7bf-b2f2-419f-84ce-c43538a9d5b1")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("b7df5c63-6a13-4446-9b7a-e859c4eecfaa")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("8d92fd2c-824b-43b0-aff6-ae7bf7a18bc1")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
