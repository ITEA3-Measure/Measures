package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("319fa8b0-8a60-4d4e-b504-e751719485f5")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("3fbb858a-ac04-4195-8c2c-bb23bc507e54")
    public DoubleMeasurement() {
        
    }

    @objid ("af9bc681-0a55-4e32-9dae-289ed613a140")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("db0ff918-2795-470a-9f61-2efb50f0508f")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("ee50a65a-31d7-400a-92cd-f5b08e09d748")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
