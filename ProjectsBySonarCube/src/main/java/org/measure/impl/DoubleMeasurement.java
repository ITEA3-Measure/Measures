package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("143dbbac-36e4-4824-846c-9f5569d664b4")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("7707be79-553b-4d27-939f-49892af67f81")
    public DoubleMeasurement() {
        
    }

    @objid ("99eef671-24c8-47be-87b0-1f274d753030")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("48cce99d-deb2-42b2-a52f-d7112adea7fa")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("1d60424a-3e88-4225-8a90-25819c47ab44")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
