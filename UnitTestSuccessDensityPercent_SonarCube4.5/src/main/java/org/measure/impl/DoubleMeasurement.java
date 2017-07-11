package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("aa8de70f-82c2-41d7-9fab-aba27afd65af")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("593db135-05cc-46ec-bc03-b46545f4fee8")
    public DoubleMeasurement() {
        
    }

    @objid ("21b0cebf-4ebd-47c1-ac4c-f588eece4376")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("f68e5afa-4d3b-428d-a0d2-f38db1e7cd9d")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("ebc19a2c-2711-4104-9d79-5b79db89df58")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
