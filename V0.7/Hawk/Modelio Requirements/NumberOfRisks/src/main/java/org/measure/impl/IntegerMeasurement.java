package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("b8bd5c3a-121d-46f6-a923-a96b95f49415")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("33a7dc03-0a95-4189-972b-963b1f3e7ccd")
    public IntegerMeasurement() {
        
    }

    @objid ("b1300213-053b-428c-a1a1-c35dcda543dd")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("dcc45ac4-2384-4c6a-b94d-e27edf7853c0")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("d2dac1b1-ae71-476b-8aa9-ee18c4a83408")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
