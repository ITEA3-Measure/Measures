package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("80408a33-f6a7-4657-89ea-42c506da0dcc")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("01172658-31a7-427a-906d-b35613a734f0")
    public DoubleMeasurement() {
        
    }

    @objid ("d4231321-d2cc-4a48-a414-c7c178c3c579")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("fb46c7f8-7756-443d-81de-349d262df0be")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("bc5c6663-bc80-4f29-9cbe-bbb2df1df623")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
