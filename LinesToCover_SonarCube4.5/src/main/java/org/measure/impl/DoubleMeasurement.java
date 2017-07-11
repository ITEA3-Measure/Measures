package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("f0a15ee6-8f72-4a16-a6df-d4e2c080535d")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("56b3de67-cb06-4234-9e20-e1a03563daf0")
    public DoubleMeasurement() {
        
    }

    @objid ("ae9d811c-e19d-48ba-8dd9-29ea6770910c")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("96e72624-4116-4b6b-86d3-b408ffab9555")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("81b9a879-df9b-4ba7-8176-96c86bb9f679")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
