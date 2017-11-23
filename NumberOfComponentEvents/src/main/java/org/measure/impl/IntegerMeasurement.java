package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("9e712552-1bda-475b-b31e-b9dab6894ba6")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("cbb1f9a9-b77c-4e88-af17-1322c07569c0")
    public IntegerMeasurement() {
        
    }

    @objid ("ad815722-aa07-4095-af09-38eddc00c131")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("9a19f39f-9f3b-40ac-a7a1-d1f4dd76dc94")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("86eff821-4ad7-4219-8206-f45f53f98ad2")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
