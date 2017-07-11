package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("21dd273e-66ed-4045-b2eb-4f6d7da23ff6")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("1bba2281-01e3-450f-840d-398f77e601b6")
    public DoubleMeasurement() {
        
    }

    @objid ("5240508a-0867-4096-81bd-e1f2f3cf0379")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("03c78634-afe2-4bc3-b1f9-4b06124e67d2")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("a5880a2d-dd01-4b85-9bdf-b2f7123a2623")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
