package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("4245b1e4-2389-492a-a6f7-5eeed7c175e8")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("9a72f339-d1a1-49b5-b95d-0d1747b78cf0")
    public DoubleMeasurement() {
        
    }

    @objid ("a2730262-9b76-4435-bf97-2a1d55400b7e")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("d835c2ed-109c-418d-8193-8e7f71f56601")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("365df2c9-2c1f-4403-9df6-d46e5ce3f180")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
