package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d4be3cb6-935f-4919-9119-c2cf9caad508")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("82a7c5ed-37e8-453e-a988-7441f3194e67")
    public DoubleMeasurement() {
        
    }

    @objid ("c3ec4e5e-65f1-448e-8a55-deeb97b53cf5")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("76dd2ba4-7bf9-432f-8d6f-9b776620e365")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("9419c699-7d01-4d11-a20c-f72c84ef7ef3")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
