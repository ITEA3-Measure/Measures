package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("e6dcc829-3161-436e-bd31-367200d62474")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("ddebd3c1-adc8-4b68-8ea4-83d2ae18ee03")
    public DoubleMeasurement() {
        
    }

    @objid ("86b78102-4d35-43e6-92c5-4762cc10430d")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("6f1233ed-acd2-4a75-bd7c-b65b7e1d4a10")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("eb6f59d6-a37a-482f-b12c-ad88917ac778")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
