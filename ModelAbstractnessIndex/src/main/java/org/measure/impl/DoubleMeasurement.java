package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8f967266-06c8-4045-afec-690079a5ea15")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("dce8a136-3ed9-463d-9dcd-f5920bd4b05a")
    public DoubleMeasurement() {
        
    }

    @objid ("1a207441-cced-4bad-a889-afb0d07a2f9c")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("3827834f-0e4d-45f0-9a36-166bdd00bd12")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("86b5b41d-5d6b-4a1f-a169-a9253afa199e")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
