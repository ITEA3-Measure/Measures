package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("70b8c3c7-2cdb-4c05-8f8e-6d212bdffe15")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("50bc50f6-cd11-4e07-bac6-db364130fed5")
    public DoubleMeasurement() {
        
    }

    @objid ("46ebd951-a039-497f-98c7-85d5912992a4")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("76a30281-bbd1-4c43-a7f4-8b331cf07c74")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("26251f73-be5e-4198-bb72-73c8c2e203a8")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
