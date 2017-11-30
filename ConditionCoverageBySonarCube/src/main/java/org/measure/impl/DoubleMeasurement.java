package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("c47d7a34-9566-4b21-a27d-eff3bd16d2ac")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("3cd38d7e-1836-4a61-8494-62bb870a01da")
    public DoubleMeasurement() {
        
    }

    @objid ("d9e30af4-873c-4b83-90d6-264477825099")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("0a183bc7-1178-42fa-a17f-f9a865521c59")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("dc0cb12f-8ce1-4271-98f8-d7a8272d1d98")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
