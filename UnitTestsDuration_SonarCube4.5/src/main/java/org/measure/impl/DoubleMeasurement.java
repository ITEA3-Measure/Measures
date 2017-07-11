package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("cd2df251-4829-41ff-8bb9-2f95448ac359")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("08ad1e2c-b610-4bc0-b70b-a97868a5155a")
    public DoubleMeasurement() {
        
    }

    @objid ("2eb37c3b-1705-46d2-8f30-e38d0cd46a73")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("572dbea6-4ebc-4993-ae89-e9414d71019d")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("3a33e72d-2cf3-4a68-8570-763a70c5ebb6")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
