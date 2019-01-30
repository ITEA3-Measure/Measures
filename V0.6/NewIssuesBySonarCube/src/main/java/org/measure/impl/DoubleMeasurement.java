package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("74d6c800-6471-475b-ba55-dc84189cc898")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("6da273fe-6a26-4ebc-bfa8-a03f600ac8ac")
    public DoubleMeasurement() {
        
    }

    @objid ("0d64eb6f-e661-41f1-84d8-ba1d6469ee9f")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("6d0b576c-e62e-482b-a21f-af282c496283")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("5f67e710-4169-4a64-a7a6-4ec516a6896e")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
