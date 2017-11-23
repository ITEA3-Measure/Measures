package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("94eb4e90-5fe9-48f5-b8bb-edeb0cff53f0")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("a6f10a90-b507-4e93-8e27-27ce7ef1e4f4")
    public IntegerMeasurement() {
        
    }

    @objid ("70db82a8-536e-4103-ba0f-407925b04545")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("e4f98adb-c4c1-4c24-b6ec-b7aca2c7e5fa")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("422ed15f-3b08-42d5-a4a9-86b1d08fb8aa")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
