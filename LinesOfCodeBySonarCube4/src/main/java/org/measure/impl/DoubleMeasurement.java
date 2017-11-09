package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("908290ee-47a0-4cf7-8a82-1669396720da")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("236b22ad-5f9f-4c5b-abeb-5e50155943d5")
    public DoubleMeasurement() {
        
    }

    @objid ("2047e9ab-e5f7-466c-9d44-183f93905b99")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("57a8f728-1041-446c-a0af-91af7452b694")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("33bbdd62-8003-4ed5-9318-8b80d609c251")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
