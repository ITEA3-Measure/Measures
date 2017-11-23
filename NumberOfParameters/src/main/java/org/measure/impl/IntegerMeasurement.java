package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("57c434f9-3336-48d1-a75b-8208b482df6a")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("c9476a5f-5b19-4f5d-b841-013bc1a71460")
    public IntegerMeasurement() {
        
    }

    @objid ("2c05d171-741f-4ea6-8925-d11c3a1f0737")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("440e8dca-6276-4c49-a1dc-56f7f3edb36e")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("ab10fec0-0a0d-4982-8e54-c62e3fa04ac6")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
