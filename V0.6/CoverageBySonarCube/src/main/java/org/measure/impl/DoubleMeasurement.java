package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("30d28341-fe02-4eac-8bea-c2a4616f4739")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("21ee2594-9090-4efb-b990-cf44dea95f46")
    public DoubleMeasurement() {
        
    }

    @objid ("55dff966-2c4d-4b47-9c6b-1897d2e466c7")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("6c88a91f-b9ce-4b40-8846-2d2958c633d6")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("bfc049b7-1788-47f8-a275-00ad536faa56")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
