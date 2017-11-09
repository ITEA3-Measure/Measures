package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8d2931d3-0773-4ded-98c9-b5cc8eb308e5")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("1bce857e-317b-41a3-9106-9e0fc7a9af93")
    public DoubleMeasurement() {
        
    }

    @objid ("229a5b64-7a19-4c1f-a4e4-dda80a7b4e9a")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("b0999a72-6f01-424a-92d3-478485a343f5")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("d0817af3-0074-4c0c-9fb6-c9fa5876d4cd")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
