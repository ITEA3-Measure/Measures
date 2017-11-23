package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("c3b3ecec-d5d0-4dbb-98e1-e92f815c0ad0")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("6ced002a-b0c9-4169-9db1-bb0657f80c21")
    public IntegerMeasurement() {
        
    }

    @objid ("a1703513-f06f-49bb-86d0-e1f250e48ac5")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("9334a9e1-d7e6-4fa8-aeb0-1761afabe175")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("d01b14d6-9cab-4e67-903d-c9144c480b4b")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
