package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("dd7f2235-3526-44f3-807c-3d2abea3d2d6")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("fbbd9b7c-7fcc-4b7d-8da9-64da6a51a83d")
    public DoubleMeasurement() {
        
    }

    @objid ("03e70556-48e8-4f8e-ad22-e52a85c022f3")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("0248d440-8671-4969-9056-698685bdf955")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("a095abe6-51ff-4109-868f-f63224968627")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
