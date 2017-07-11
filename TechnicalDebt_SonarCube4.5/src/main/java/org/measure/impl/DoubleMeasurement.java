package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d4be3cb6-935f-4919-9119-c2cf9caad508")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("94369d26-65c0-40e7-88da-b383ae0560d9")
    public DoubleMeasurement() {
        
    }

    @objid ("97ce36eb-0e80-4f97-b78e-c2eca7030bbd")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("271f76a2-ac8b-4cb7-925c-d5c5eb8fe4b6")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("63c7cad6-5e20-47bc-b08a-d40463930bff")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
