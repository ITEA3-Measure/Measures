package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("06226ffa-0eea-4474-82d1-d8b78c9d31ee")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("0d3af6b2-1603-4a63-b4d9-be0df104f427")
    public IntegerMeasurement() {
        
    }

    @objid ("4fd37b0b-b9b4-4f75-84e3-2058909140c3")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("11d40d49-6054-4012-8376-1fbf9fc6fd13")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("1dd3807d-afcc-43ff-921a-d95b9851f535")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
