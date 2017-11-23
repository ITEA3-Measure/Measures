package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("58e04e90-f4bb-4c1e-8306-48d1b27bc456")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("9288dda0-8ad9-4347-8cfc-2ae9c7a7b5cd")
    public IntegerMeasurement() {
        
    }

    @objid ("8eb58e91-7e8b-47b6-8148-e0e6b7ed7db2")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("470022a4-6fd7-4d58-a6f1-22b8a29fa553")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("fe93079b-4bef-4fb2-bcb6-c4d1207dad58")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
