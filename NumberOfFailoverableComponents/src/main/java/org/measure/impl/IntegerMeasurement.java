package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("28795e45-91d0-45c0-89cc-107ccbf069a2")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("d9c88aa6-939f-4080-9ba2-2995f88b0ab4")
    public IntegerMeasurement() {
        
    }

    @objid ("74e06452-069a-4476-91b8-0f24fd6449dd")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("50544c60-6095-4054-81ab-9a4f3f9cf0e7")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("f9885d7b-f1be-4d34-a521-4c0bb232eeaa")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
