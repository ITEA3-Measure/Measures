package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("4727c604-3410-44cb-9f6a-62f910e0b4ca")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("2b3e717b-bb06-4c9e-9047-35d00b83a96e")
    public DoubleMeasurement() {
        
    }

    @objid ("3b062dff-42ad-41f3-b7c4-e0b8083cf2c4")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("babc263e-1e44-4fae-a5db-1b59be1fcfeb")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("232ef923-be3d-4295-996e-fefc58b92fba")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
