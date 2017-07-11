package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("65b22229-3d53-4701-8483-c468091ced01")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("b77c4732-7f7e-471f-a05e-96cfc1c917f6")
    public DoubleMeasurement() {
        
    }

    @objid ("48548bc7-a901-47ff-b37d-ea305c78ea98")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("ee0ba66e-3ac4-4579-98f8-118fea197d1a")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("d53cb7aa-6329-4ab7-a071-c350801b0bc7")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
