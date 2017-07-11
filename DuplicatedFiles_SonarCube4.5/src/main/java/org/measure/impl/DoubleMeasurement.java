package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("06bcc1c3-6a05-4492-933e-b4bb057b4a28")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("e1ca100c-14af-4575-ae56-e9e8572907a3")
    public DoubleMeasurement() {
        
    }

    @objid ("c8348d9d-3058-4129-bef5-bd355c89d4a3")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("78b16404-4c63-4f2f-bdb7-73b0b14409f9")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("65a55966-83f9-4952-9052-00cc036e3b11")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
