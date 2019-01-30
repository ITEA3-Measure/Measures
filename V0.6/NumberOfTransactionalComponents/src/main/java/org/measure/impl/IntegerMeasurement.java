package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("a658cf21-2191-42ac-b030-53b0c9f795d4")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("1827d7d9-524c-485c-9545-0ce00f8483b9")
    public IntegerMeasurement() {
        
    }

    @objid ("9085e7e0-b362-4f71-8fde-5ab050feeedd")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("88af14aa-1b2c-4f8d-99da-0411dbb920b7")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("2b99d353-28f0-450a-8817-513de6ee05be")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
