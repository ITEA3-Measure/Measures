package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("75227134-d5e3-4e06-aff7-88ec3fc28ede")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("4dc54622-c2df-4766-aaa9-99bedfdb7207")
    public DoubleMeasurement() {
        
    }

    @objid ("897ad74f-2c3c-4563-8389-ebcdddb8bdd0")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("28b0d507-48d3-40c0-8fea-9df051f736c8")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("b1606e65-756a-4ccb-a13b-41ea539580ca")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
