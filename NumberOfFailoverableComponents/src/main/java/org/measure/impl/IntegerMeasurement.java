package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("fa558d4f-8b2b-4995-b61f-a89a48e25294")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("8ff2abcb-45ac-4b19-8c71-4d0c21b69617")
    public IntegerMeasurement() {
        
    }

    @objid ("1f55afb6-6608-4442-bbee-c9248d0f6459")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("7c82a5d4-d703-4383-b714-08e794105d73")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("f854e4d1-b203-4408-b9e0-4789bb2b7d35")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
