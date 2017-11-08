package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("305bae62-432a-41cb-bd75-c3f104a8e78c")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("8c3e9c5b-5a2b-4af9-acbb-68d333862e36")
    public DoubleMeasurement() {
        
    }

    @objid ("12d732e6-ee53-4a3c-b12d-bf733064a74d")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("659a0ef4-95f5-428d-84ed-686dc467c706")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("56421cd7-5b6f-4d13-9c88-f66446e7d35e")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
