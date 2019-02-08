package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("01eba3ac-c4d6-4721-ae6e-c9f6eb48d1e6")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("d67a634f-4d31-4628-9a8b-7bd61d295c19")
    public IntegerMeasurement() {
        
    }

    @objid ("5c96276f-d956-424c-bd14-750e438e4972")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("4be2ffba-2678-4b2c-9d12-f2f3dcb9e972")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("75ab864f-f051-42e5-8074-c8682d01dda3")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
