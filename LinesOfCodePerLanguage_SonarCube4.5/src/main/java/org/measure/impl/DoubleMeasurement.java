package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("635c05db-0bd1-47f4-b7ec-2b3abf2698a2")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("1868efe9-4bca-47bc-887f-d37de8fdcc83")
    public DoubleMeasurement() {
        
    }

    @objid ("f1f7e6c2-8300-4d97-a6af-cc399880c83f")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("c41dabd5-6e77-43c2-8b2e-ca3219e4c573")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("0d8325e9-f064-4026-9dd0-cf9764a9cb31")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
