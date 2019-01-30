package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("871b4940-3bef-4899-9a08-3aa2a308cb6e")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("bfe86895-b70a-42a2-b458-d00eb23c8e1a")
    public DoubleMeasurement() {
        
    }

    @objid ("0835555c-9d58-4e52-b80c-95a694d49ddd")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("c793fbc3-66ca-40ba-ac33-14fc441d648e")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("89529e6e-bde0-4949-8279-a5b1b63d863c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
