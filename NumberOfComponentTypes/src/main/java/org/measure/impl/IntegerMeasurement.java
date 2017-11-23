package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("be4d2e82-d3b9-4d06-b641-a8012e952096")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("59193eca-68c7-4aed-8e92-d458d5b171fa")
    public IntegerMeasurement() {
        
    }

    @objid ("3690218d-d5cf-422e-b3e3-3bee6aef7e29")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("3394c873-9204-4fc0-8e47-11fb79f7a1f8")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("6c9620cf-7f81-4a6a-8155-059c0b9f2f93")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
