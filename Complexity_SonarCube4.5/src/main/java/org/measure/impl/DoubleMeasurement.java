package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("6e1b30c8-58ef-45f3-997a-9ec23d98a352")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("4c850ccd-d008-4674-804a-9a22465374ea")
    public DoubleMeasurement() {
        
    }

    @objid ("ee3cbce9-a92f-4ecf-8221-0b27080a7775")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("61054987-4ddc-466d-95c0-053f989f16fc")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("459607a2-910e-4b98-aef4-53dca2697425")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
