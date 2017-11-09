package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("977da2fd-1d10-48f3-b0f8-9b3c3cfe1e6a")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("4d5c1af3-5694-4a2e-8f7d-c1b730650f5e")
    public DoubleMeasurement() {
        
    }

    @objid ("31336a0f-a8c4-4909-a9b8-03fb52c9da36")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("04b487b9-1a5e-471d-8e5c-4723065b1fcc")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("534d56fd-964a-4736-86be-42c215d05939")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
