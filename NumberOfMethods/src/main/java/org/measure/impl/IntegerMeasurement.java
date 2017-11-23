package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("83cf2a53-7888-4703-91d2-3b6d890e0767")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("c2852271-7764-41ba-86ca-0737fb37279d")
    public IntegerMeasurement() {
        
    }

    @objid ("eec9f8c6-d8ea-4797-bbce-1ee463e821da")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("e858512a-f740-4ce7-805e-8b3c3e6c0a62")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("d25fcce3-6848-4dca-901b-64b3d84e4be9")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
