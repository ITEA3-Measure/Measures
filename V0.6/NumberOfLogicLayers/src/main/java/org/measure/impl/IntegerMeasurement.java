package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("94491f6a-f582-414f-a957-9601f29ec1f1")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("3580bc14-18ba-4b09-be5b-55195acaa198")
    public IntegerMeasurement() {
        
    }

    @objid ("4c372c64-488e-439b-b68e-f42e8a211678")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("b105c494-6286-48e9-b2db-a1ce953487e5")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("73900375-b129-480a-9d11-3aedc561197b")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
