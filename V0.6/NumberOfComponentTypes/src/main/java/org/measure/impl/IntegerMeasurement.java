package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("a6d83d4a-ca2d-4211-9050-cbf085c2e588")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("d6eb6dba-e780-475a-9038-674b487002f9")
    public IntegerMeasurement() {
        
    }

    @objid ("eb6e7197-6f0c-4e04-9b42-c8de25a6f69c")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("c25f92d5-4f7e-4d07-bae5-49551b614484")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("3a860a34-cfcf-4dcd-a8ea-04885d7df589")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
