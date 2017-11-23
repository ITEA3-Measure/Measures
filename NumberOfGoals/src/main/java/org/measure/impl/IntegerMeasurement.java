package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("b39b0a26-51d3-42c9-a07e-54f250739e1e")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("057970fb-e91e-46e0-b262-78a768385012")
    public IntegerMeasurement() {
        
    }

    @objid ("0a37a0ff-8376-44ab-9329-422c3bc391d1")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("f0a11557-fb37-450e-81fe-641eb5d5775b")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("d14d6d90-a2e5-47a6-b07d-46385042f3d5")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
