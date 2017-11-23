package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("75b1f3da-1c0f-4f75-a4b7-3dbc3aa15a78")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("04a8d8c0-42a8-4ad4-9ec1-bacc5da81669")
    public IntegerMeasurement() {
        
    }

    @objid ("4ef7dbfa-7006-48d8-95df-bcbd66bcc386")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("be10c6c6-e839-4847-999c-98e2f9726fa6")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("5780b829-7f1d-450e-b96f-fc6d9f4bff2a")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
