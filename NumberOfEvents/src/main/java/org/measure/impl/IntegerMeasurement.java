package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("e616252b-a203-43de-8110-cc8b84833183")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("b66fcbe1-1e84-47fc-b230-fc776bd32204")
    public IntegerMeasurement() {
        
    }

    @objid ("6f00164d-7ff8-4397-8b13-fc23b7f48296")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("f56246e9-37b4-450a-aa84-112c39bbba52")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("285fa5fd-c00a-41dc-82a4-8299b8dbdf89")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
