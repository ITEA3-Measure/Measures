package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("cf88322d-f50d-4592-a6cc-75d1d1b1eb52")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("4f1c301a-a0cf-49b4-af80-1c8fafd648bc")
    public DoubleMeasurement() {
        
    }

    @objid ("b4e54ffd-5b5f-4858-a4d0-e028fa4e311d")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("11548f3a-747d-4106-b39e-4a997ac10577")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("40c84483-651e-4c41-87da-8feae229cbbc")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
