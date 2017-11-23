package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("ef15365b-7882-488c-b709-f27e0c5ffbd0")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("9d25948a-f6f3-4b2f-97bc-5331ab423867")
    public DoubleMeasurement() {
        
    }

    @objid ("8a31e313-c4f6-4e06-b0a8-c6d53a74ed04")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("6fdcb432-bd03-4711-ada1-e100a5743bae")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("ba6f4430-83b6-49eb-af96-93e8cac78f8f")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
