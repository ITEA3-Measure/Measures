package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("20863ebb-16ef-4083-b7e2-4ab67f257fe1")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("84d28a66-1d37-425f-aedd-1d6e179d0e22")
    public IntegerMeasurement() {
        
    }

    @objid ("6fa58a57-25a3-43a8-ad41-1205b064e006")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("f5998ddf-c82c-4158-b41a-b35ae60cf129")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("3fcd5a66-72d1-48a8-87eb-a0a60884b6b0")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
