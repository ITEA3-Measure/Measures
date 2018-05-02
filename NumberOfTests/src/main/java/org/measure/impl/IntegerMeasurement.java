package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8f836685-01ad-435c-a108-d33e46bfd179")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("4bc1404e-c64a-4d37-abcc-90e57c2845be")
    public IntegerMeasurement() {
        
    }

    @objid ("c1a64416-ab1d-4c4c-9cf6-78c3626417c4")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("d0528473-dce6-4433-86f0-158a1dc1dca2")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("c55a72ac-441f-408e-bafe-a92d8a1a9ae5")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
