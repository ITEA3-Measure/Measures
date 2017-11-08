package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("65b22229-3d53-4701-8483-c468091ced01")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("f63e06bb-6286-4d80-998b-24cefdc2280b")
    public DoubleMeasurement() {
        
    }

    @objid ("87e9a1a6-0e9d-44d2-b080-b1c8cbab5b6d")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("ac2dc47e-152b-47cc-9c1c-2241ecfbcc96")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("398ec3d5-944a-4b43-be75-a5760960558e")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
