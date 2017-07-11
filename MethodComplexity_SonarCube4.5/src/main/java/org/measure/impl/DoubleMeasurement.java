package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("02c3cb08-ee4b-4d63-b653-b02140e7ff6d")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("03584153-0a1d-4b9b-ac94-162d4a9ba69e")
    public DoubleMeasurement() {
        
    }

    @objid ("93cb6f98-1dca-44dc-9072-07d7a7e74312")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("1bf82feb-af79-4a79-acfc-61480efe68fe")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("c190584c-e352-4687-813c-a011bd187ff4")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
