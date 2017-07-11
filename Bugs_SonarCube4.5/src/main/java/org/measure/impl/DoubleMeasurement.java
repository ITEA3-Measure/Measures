package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("47319f99-f602-4499-8067-5d9654fc0c7b")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("05e1e3c2-c36c-4e32-a52c-e93fe61c14c9")
    public DoubleMeasurement() {
        
    }

    @objid ("0cc580a4-799a-42af-8765-7f17e407da74")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("1eb85885-5a50-4ba2-92d5-a963fa08d545")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("d25e9a0d-1885-4093-b49a-a340af1d0545")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
