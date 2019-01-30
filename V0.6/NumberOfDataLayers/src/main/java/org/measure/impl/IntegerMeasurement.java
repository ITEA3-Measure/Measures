package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("193b8e5d-59b1-4ce6-9587-00e5d9fcd9e1")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("843e9abb-7e55-4467-850a-b85f80a75306")
    public IntegerMeasurement() {
        
    }

    @objid ("2f7a48b6-28e0-4287-98e3-e0ea7bb1b674")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("97c9fe3f-5b99-44a9-9274-f0ffb3a236b5")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("0ded4884-12bb-4577-a2e8-73c9a439a77d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
