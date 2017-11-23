package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("36d0c57b-3347-4d1f-94a8-f90d40ade9c5")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("1d08ddd9-e1ef-4134-8482-446425ca8df3")
    public DoubleMeasurement() {
        
    }

    @objid ("c275ba55-3663-4b6c-8184-4f022e17fdce")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("f25a3030-ac1a-4656-8299-c3b743f75992")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("93fd33e4-74f6-404c-ae84-a874650f9e4d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
