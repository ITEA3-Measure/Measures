package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("1e53b3da-82a4-4af7-b09d-6c9ff68da61a")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("39e7be37-e915-4caf-b976-2cdbeea71c94")
    public IntegerMeasurement() {
        
    }

    @objid ("d32a52fc-936e-43f8-a18f-7a3300dac730")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("a7b305ff-3d44-45b5-97fb-f5c05a4bbe16")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("de42c7fe-106a-4a97-9b1c-622edbfd31bc")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
