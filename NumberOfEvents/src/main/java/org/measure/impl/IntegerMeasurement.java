package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8e29b7d8-36db-4712-8e38-253ba5d6e0c7")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("739ff47d-b5a4-4306-b045-33e0fd87c05d")
    public IntegerMeasurement() {
        
    }

    @objid ("f04ae871-a27d-4c9c-8af7-52ba3dc17c41")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("aad01222-0a98-4777-9178-94988c92f8c5")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("841e31af-4e5a-4d56-9577-1e7a9a8bf498")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
