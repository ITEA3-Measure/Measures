package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("29a2deb7-c5bf-4ba0-bcd2-c464f3378574")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("bb0b19dc-fde6-456a-b08c-7fda12684a5c")
    public IntegerMeasurement() {
        
    }

    @objid ("de840c9a-3ed1-41aa-b346-2a6eaba75c0a")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("97404101-457c-4e98-bb8e-62e0c711770a")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("27571c77-5c7c-46bd-a3af-a8ba60432a7f")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
