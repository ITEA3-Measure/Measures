package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("a3636f0a-9306-463f-b7fa-fc41880d1ff1")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("9c00bb6a-e0ed-423c-aa25-516e7e2e4ae7")
    public IntegerMeasurement() {
        
    }

    @objid ("166c627f-e6bb-44e0-a0d0-4e8b478e83d3")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("98e6da06-16c2-4131-9648-2796fb61059d")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("a66e6d6c-b8a8-44c1-9fbf-faba835f1908")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
