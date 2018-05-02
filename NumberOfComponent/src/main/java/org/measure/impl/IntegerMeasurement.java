package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d4881d8d-5685-4cb6-84d8-91e1d98375a0")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("ebcebc7b-3cc0-4b3e-8647-48be4f1a5b8e")
    public IntegerMeasurement() {
        
    }

    @objid ("5d52098e-7e7d-4b0f-8f94-06c9ed1f6e0c")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("96b9edd9-469b-4d4d-8494-b6ca0a56a037")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("a299fe0e-f5a1-441a-8a7a-690cda05a8e2")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
