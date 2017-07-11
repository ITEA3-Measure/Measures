package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("5ca57b05-d8e6-482f-b234-5e3e299a05d4")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("343f5590-f558-44bc-84a7-17e59db2d721")
    public DoubleMeasurement() {
        
    }

    @objid ("8757e286-b1f9-46b2-a2d8-3abc71451e78")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("0b55a73f-e6ea-46b0-b9dd-e8664922c0ba")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("5a6be4a7-d146-42e0-9dc9-c95cedcca22d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
