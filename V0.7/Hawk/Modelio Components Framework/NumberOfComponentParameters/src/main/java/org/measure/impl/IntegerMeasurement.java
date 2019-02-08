package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("3ccfc99f-5c70-4df0-926c-d602f2f4b9b0")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("d8ff2f56-05a9-48ed-b620-787ca5af2d65")
    public IntegerMeasurement() {
        
    }

    @objid ("67f656b2-1d70-4499-9009-0820ac8a2fdc")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("328b6dfb-8a79-4a5b-8143-b59cf976ee89")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("23651c64-8561-484a-8d65-a81025852b72")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
