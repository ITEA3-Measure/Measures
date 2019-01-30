package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("43a75317-2c6c-4f30-8899-8e0bec01e023")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("a2a1512c-2763-4689-9acf-9d0b985ff0c3")
    public IntegerMeasurement() {
        
    }

    @objid ("e10452b1-5f05-499f-b95d-f01db0c74691")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("a9e594cb-a5d7-402b-acf8-97d80194ff1a")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("e82b91ea-eca2-4a17-8c75-967023557a22")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
