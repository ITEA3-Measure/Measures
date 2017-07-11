package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("7c8c4a6f-5e85-4d81-b309-f5629a6ba070")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("8b8af245-2d93-4ce2-8188-b408a0b885ac")
    public DoubleMeasurement() {
        
    }

    @objid ("0d2577ea-fc34-49e8-a1c5-c2286a69fd0f")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("68e4aea9-d289-4de2-b46b-d2e5a6eac340")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("3c0cadaf-293d-4e74-bc75-c30fc2f1a292")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
