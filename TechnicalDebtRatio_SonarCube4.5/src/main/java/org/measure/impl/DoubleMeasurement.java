package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("5b8e593a-b79a-4d16-87e2-bbc5fdc68eab")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("b04c7169-d001-4005-8ee7-c5e4d39439bb")
    public DoubleMeasurement() {
        
    }

    @objid ("b51bf9fa-3db4-472f-83df-cf202d834d69")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("aac96966-f0a7-4c76-b83b-206104969f0b")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("2e2ce1a3-616c-438f-81a9-f7bc30b09874")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
