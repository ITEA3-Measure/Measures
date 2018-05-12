package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("bce8aec9-0b37-47d4-b61b-3ffc937ea2fa")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("52a4f044-02fa-443c-bf9f-c021214cde7d")
    public IntegerMeasurement() {
        
    }

    @objid ("3e564bd6-c329-4334-ab03-6fe525fe101f")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("34da0ca0-332a-4819-bdee-ebb5b3452af0")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("b6b2be06-ec36-4c91-aaa6-8b34fe5fd3f4")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
