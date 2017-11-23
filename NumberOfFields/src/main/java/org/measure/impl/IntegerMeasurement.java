package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("95fce5e2-17be-4f6b-b75f-bd272fdd77e2")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("14844e04-57f7-4358-9259-7425d050d1ff")
    public IntegerMeasurement() {
        
    }

    @objid ("63e5323a-9865-4add-b1bc-cd622d94fcc6")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("c7400be4-d5cc-41d4-9999-7107ea123c3c")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("136e0a54-4739-42dd-bed3-ede3e1b93a49")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
