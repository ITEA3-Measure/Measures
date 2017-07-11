package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("977da2fd-1d10-48f3-b0f8-9b3c3cfe1e6a")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("c6cdaa9c-4396-45a2-a246-12f41419876f")
    public DoubleMeasurement() {
        
    }

    @objid ("bbd33e86-d912-42be-9c12-88a221f29728")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("1b8f2f44-72b4-46e5-ae19-1330a273f919")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("6dd34f93-9f80-4a7b-a808-6baba55c14ad")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
