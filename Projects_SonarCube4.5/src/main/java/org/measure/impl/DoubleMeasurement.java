package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("143dbbac-36e4-4824-846c-9f5569d664b4")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("17eb23e4-3c6a-4f49-ac78-580ba8226fcb")
    public DoubleMeasurement() {
        
    }

    @objid ("bf7e7127-fe81-4da3-b807-6d8131566f2b")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("bfb5684d-fe01-433f-a9c4-43f1e3b31cc5")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("098506cd-cb2b-4bda-b1dd-0703c4a7740c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
