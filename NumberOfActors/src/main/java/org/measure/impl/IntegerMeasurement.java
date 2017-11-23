package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("7fe24005-2de2-46d5-be51-74bdec2f3604")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("4a38d920-01aa-4e7c-9ee2-63d311ebb97c")
    public IntegerMeasurement() {
        
    }

    @objid ("f30ef8e2-ce1d-4d40-9911-89f63f2912e2")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("b6111dfd-a960-4956-aa71-6ab4257b5a75")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("6b523d80-b83f-45d1-8d34-09b8a6ddaff9")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
