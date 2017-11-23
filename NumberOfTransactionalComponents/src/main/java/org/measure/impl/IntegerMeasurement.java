package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("3ad9d992-c671-47e3-89a1-0b8d4b9c5bf1")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("5eabb8c5-dc5a-4764-b8cb-47e0959c9f03")
    public IntegerMeasurement() {
        
    }

    @objid ("1a6450c9-182f-4ee2-8c17-17a0116f752d")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("889a134d-056f-4852-bf26-cae02a0d0cc5")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("444ac0eb-3553-4456-a80a-8f4d2513de81")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
