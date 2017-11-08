package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("47319f99-f602-4499-8067-5d9654fc0c7b")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("e5a87e62-74e8-44b4-8c36-ab76d9949ea5")
    public DoubleMeasurement() {
        
    }

    @objid ("c4adf193-9c0b-458d-b565-cfdd75641e65")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("de501d1c-a852-48ae-af8e-d177b416f2ed")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("2487ef23-7c26-4024-a3c2-32131a0258f0")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
