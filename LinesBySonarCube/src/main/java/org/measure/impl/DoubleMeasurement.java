package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("ecc334f6-d3ab-4864-a794-2d33664cf871")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("8e078c7e-55be-4259-a21b-d34f7f8931c3")
    public DoubleMeasurement() {
        
    }

    @objid ("8d69b819-b7ca-4f55-95b2-52c3e1e367af")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("b82bce6f-ee1e-4ee8-b06f-b13c88c2d56d")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("65544c92-a7ca-409d-8099-9d282baa0100")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
