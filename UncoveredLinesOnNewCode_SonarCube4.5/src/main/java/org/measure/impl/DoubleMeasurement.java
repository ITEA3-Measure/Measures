package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("e69e8c30-e377-402d-b5ef-e40f6cc219fb")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("f8e64aa2-cf6e-46ba-af18-f87e7a65609c")
    public DoubleMeasurement() {
        
    }

    @objid ("f2b037a0-1fba-453c-9f6b-cd6693e387ca")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("0e9b47b2-bc47-4028-8783-75f4117355a0")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("3b06ac91-a788-4463-8c2c-e41072695885")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
