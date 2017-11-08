package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("02c3cb08-ee4b-4d63-b653-b02140e7ff6d")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("69ce1bb6-8d26-45a5-a2a0-cd476637d61e")
    public DoubleMeasurement() {
        
    }

    @objid ("b4bf5613-6f18-4cb6-aed8-0ddc7d9e2e1d")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("c62d5e94-502b-403c-8963-dd3526212de2")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("60e8aaac-8946-4379-a90d-b1d64fdd89e9")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
