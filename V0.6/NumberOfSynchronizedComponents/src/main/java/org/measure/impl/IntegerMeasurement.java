package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("7addd5c8-965e-49e5-9e5b-0b5a65d6f373")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("efd9bc0f-37db-40b6-a930-234da4b28904")
    public IntegerMeasurement() {
        
    }

    @objid ("ba2e630b-8f30-4ef3-a5aa-4087d76fa0f6")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("ab39542b-b879-4f45-b04b-9fd95a75ff0d")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("6a1de617-5276-43ab-8d72-5a9070059309")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
