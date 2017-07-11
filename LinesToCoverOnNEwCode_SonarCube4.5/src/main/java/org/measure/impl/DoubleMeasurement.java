package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("27d58841-49cb-4986-85b5-3c22f6f176ea")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("d2f16947-3e28-4794-93b2-ec02222c82ac")
    public DoubleMeasurement() {
        
    }

    @objid ("e8f2ae35-19d7-49fe-9efc-b596b32ef862")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("55495114-48db-4512-aa0c-4898402a7753")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("1200af0a-4ad8-436c-bfba-3dd145e397f2")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
