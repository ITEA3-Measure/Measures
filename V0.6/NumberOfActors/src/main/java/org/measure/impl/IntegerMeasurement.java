package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("a01ddb32-2176-4730-998e-926a9e81581f")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("e971e775-b0ef-4d1e-80e6-f01ec5dcd7e1")
    public IntegerMeasurement() {
        
    }

    @objid ("4ac74cb0-fd85-475b-a707-ad202cbe39af")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("411cd183-65dd-485f-945f-54eb0789bb52")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("c4145bbb-f815-4132-8e62-c821bfd9832f")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
