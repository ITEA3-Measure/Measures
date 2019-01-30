package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d6a583a1-c622-4008-a4cb-219fa9827447")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("f5f56c2c-1a93-4ed7-bf55-0ace4ae680f3")
    public DoubleMeasurement() {
        
    }

    @objid ("ab0509da-fd73-4eac-bce6-d07f3b19e5e2")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("724a8b2a-b24e-4564-a41d-278e4e718ab1")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("1effebe4-6b3a-459a-957e-0381eceb800f")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
