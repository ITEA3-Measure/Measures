package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d6a583a1-c622-4008-a4cb-219fa9827447")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("371154e8-e71a-40c6-ac90-f5d3b85cc753")
    public DoubleMeasurement() {
        
    }

    @objid ("97233cb8-70b7-45fa-8f02-bc423c2098ff")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("03290cb6-390d-4c5e-a0b1-91cb2800d899")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("51d06458-fc1f-480b-9163-fcf5e233cb78")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
