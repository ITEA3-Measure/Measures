package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("3c3dff48-b725-480e-bd94-7dbf8a5c0cb2")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("cb59a76d-aa8b-4ecc-a5f7-605da169a933")
    public DoubleMeasurement() {
        
    }

    @objid ("53a5bee9-209d-4f96-a738-9143c2115227")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("1f4fc024-5993-4bf4-8a24-c824c4088721")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("17ec0d93-011c-4055-bb11-3327c87a6a0d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
