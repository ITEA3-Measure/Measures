package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("a169fca2-a7f6-4ba5-b907-24ceddbc6c24")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("23064e72-5cb9-465f-ac39-4544484a72f7")
    public DoubleMeasurement() {
        
    }

    @objid ("5e7e3912-3b56-45ba-9514-0162aab3dd88")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("2e68ae74-649d-42d3-b87e-99ac1e43d04a")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("a68ca5ba-128e-468d-bb7c-2960a56db3d3")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
