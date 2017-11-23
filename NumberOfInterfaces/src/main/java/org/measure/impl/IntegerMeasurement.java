package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8c3f16c7-15aa-4d60-8f66-3ccd573d4780")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("dc53bff9-1def-418a-99b8-515321c13865")
    public IntegerMeasurement() {
        
    }

    @objid ("b7a35254-4a3f-40da-a3ca-7bb89e353115")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("f2648cd6-ec0c-4682-a5c7-d43321dc50a0")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("dca3c6cd-f5c4-4894-94d3-dbbacc2e75f7")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
