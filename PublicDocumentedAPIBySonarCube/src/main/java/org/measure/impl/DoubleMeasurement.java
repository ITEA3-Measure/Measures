package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("1c6ed25d-f5bc-4b73-9458-366ed04aef2b")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("bc1c34a4-3321-4288-b623-7e0fbda42745")
    public DoubleMeasurement() {
        
    }

    @objid ("dffa361f-74bb-4236-99df-18f29a46e481")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("3542a140-b77f-45e9-a66b-53058351852b")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("c593606b-5b27-479d-98bd-845af567c174")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
