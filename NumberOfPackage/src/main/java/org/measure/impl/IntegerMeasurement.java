package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("21c2c768-03a9-4660-8199-86fdd4d0cc9a")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("3c59d415-aca8-4a36-be30-57cc744df5c0")
    public IntegerMeasurement() {
        
    }

    @objid ("06013fba-4c09-4c62-a6d4-ab6fc88b4d6a")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("bc3e6cca-5c21-4f43-b021-0cf56719b9c3")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("83407e9b-10bd-425a-bad1-1207dddeaf91")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
