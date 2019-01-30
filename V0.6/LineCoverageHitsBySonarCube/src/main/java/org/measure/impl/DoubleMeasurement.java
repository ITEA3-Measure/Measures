package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("4f09f7e9-ec1e-4ab2-87c9-0e87823c9992")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("f092c555-7c78-48b1-bf21-b02003b972f7")
    public DoubleMeasurement() {
        
    }

    @objid ("070b78ef-146c-45a5-ac30-6de0cdeaf490")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("55aa8fe2-7151-4bcf-bc1a-e1a3bc5a6616")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("9e81b895-65fe-47a3-8c0a-96d980601c84")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
