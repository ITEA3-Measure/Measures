package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("332335de-bd4a-402a-9b6c-c302d093c4dd")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("335153d4-64a1-4f99-a3ac-1df0a7a0ca3b")
    public DoubleMeasurement() {
        
    }

    @objid ("222df2e8-b499-475e-8379-d598183a09d4")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("3b42e8f1-46a6-425e-b542-0495afcfe756")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("84b6677c-d03f-4a56-b811-3d41baee46ab")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
