package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("de7abff2-b0df-411b-962e-cda19d0a20a9")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("ec5034df-b6c8-451d-b8b4-99055dd364bc")
    public DirectMeasureData() {
        
    }

    @objid ("5ce5440c-e605-400a-8774-228886f97ff1")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("65a09991-ea2b-48ad-bca9-b00611b161de")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("5a07e9d0-5493-4525-ae5d-7c451fbaf2bf")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
