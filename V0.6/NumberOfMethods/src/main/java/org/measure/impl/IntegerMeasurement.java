package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8c21a83c-3a21-4d13-94b1-6b5bda096854")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("1a84b3bd-476b-4bde-87e2-3a1833da2276")
    public IntegerMeasurement() {
        
    }

    @objid ("08077622-d138-4a32-83f8-fe018b204440")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("182f90fa-135b-4657-be77-4b68fa8e7ced")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("7916f665-e589-4117-8ca1-c40508892073")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
