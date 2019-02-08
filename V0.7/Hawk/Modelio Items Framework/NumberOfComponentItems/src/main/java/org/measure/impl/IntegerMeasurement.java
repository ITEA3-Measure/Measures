package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("4e6232b0-1d80-4629-98cf-13789cb06e3e")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("b3278deb-e258-46d2-93fa-fc35d26a0e5a")
    public IntegerMeasurement() {
        
    }

    @objid ("a54d86ae-4e6a-46e4-944b-93d0836343bc")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("c2b767c3-6e51-46c0-bf01-0f874a4d234b")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("03e3dcd2-ce2f-4465-bc70-831e19584fe8")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
