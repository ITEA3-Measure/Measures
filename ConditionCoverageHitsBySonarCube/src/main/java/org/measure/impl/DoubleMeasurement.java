package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8851d8b5-8cb7-40ff-8774-c3d90694fdbe")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("6eaf2247-5107-44c7-9827-652e3b777f6c")
    public DoubleMeasurement() {
        
    }

    @objid ("6ca8200d-c914-4822-94e7-cbf37d6b8e25")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("5548589f-24fa-49b4-b45f-092a2dba54a8")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("052acba5-e26c-48b0-9440-b95577ac200f")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
