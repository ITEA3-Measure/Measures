package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("7c8c4a6f-5e85-4d81-b309-f5629a6ba070")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("3e162954-216a-4877-8c8c-df165776abcc")
    public DoubleMeasurement() {
        
    }

    @objid ("1a538ab2-9962-42c4-a6bc-9ec6cde2438c")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("a3b58996-4c72-4603-a283-503a2801c7ae")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("ff9397aa-7998-4ff9-9687-f901c3048023")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
