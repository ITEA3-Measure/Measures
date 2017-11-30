package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("7d72e47c-f27a-4445-b40a-b0cb9589e82b")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("a45e296c-2a23-4082-b303-e1a688b89a35")
    public DoubleMeasurement() {
        
    }

    @objid ("9c6f80ff-dd5d-47c4-88e8-cc03e9430a0f")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("4bae4a69-c699-4477-b898-3cf2f07eb54d")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("5d6479c4-68fd-4ed3-b080-68f5ce6f3974")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
