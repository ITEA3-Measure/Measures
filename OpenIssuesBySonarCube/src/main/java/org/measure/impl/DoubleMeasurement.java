package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("09e3412d-9a68-450d-a44a-22e5144fb47e")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("225e1e42-cffe-4123-a82f-30b663951104")
    public DoubleMeasurement() {
        
    }

    @objid ("d3d33e06-1c43-4dec-95d3-748f4e6e18c0")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("9db9b6d4-d04a-4a86-9830-7db6c6e35840")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("06797ae8-ce5d-48af-88b6-cf78dc446446")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
