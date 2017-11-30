package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("cd2df251-4829-41ff-8bb9-2f95448ac359")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("106b34e3-e6da-4b82-adda-8650ea06abbc")
    public DoubleMeasurement() {
        
    }

    @objid ("9887555f-1cf4-460a-b2d5-95885684c493")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("763a8c46-727c-4f2c-a52e-a7a8c6a4bc5d")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("01e79f6d-4d7f-443d-910d-5d8399bb4b91")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
