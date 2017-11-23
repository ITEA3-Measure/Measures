package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("3170bb72-f3a6-4f15-9c80-dd83258d522e")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("799a0b3f-bb5e-41ac-b0c1-831ceb6031a7")
    public IntegerMeasurement() {
        
    }

    @objid ("648cb9f3-c221-4bcf-b416-cbe316acfe38")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("ee7c82b4-9fa7-4a9c-9715-6b06c8d8df4c")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("0ab268e7-96d0-4ee1-aa6c-b812e00b1c0b")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
