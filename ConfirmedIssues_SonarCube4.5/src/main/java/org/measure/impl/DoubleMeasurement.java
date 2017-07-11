package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("0c99ba1d-4cae-43b1-9429-9fb46535a082")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("cd1244c5-8446-45b1-8c88-2c9ead246dbe")
    public DoubleMeasurement() {
        
    }

    @objid ("19bb0808-2dd3-454b-b18e-a4bd86130cb3")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("38a7f52c-bab3-405d-9d8f-23eacf554911")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("caae86a7-4658-47cc-b6ed-03283576fc4d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
