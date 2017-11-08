package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("44d0bf4d-61a1-42cd-b34e-dd1a79f64875")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("54ceba5c-6f06-4b5e-ab7b-5b31fc51abea")
    public DoubleMeasurement() {
        
    }

    @objid ("d8a8acd7-f5bc-486a-b16b-420d9c004140")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("8bbf1316-459a-497e-8d6e-cfaf3f303e9f")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("c4158ddd-77ac-4432-adb6-68582e929910")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
