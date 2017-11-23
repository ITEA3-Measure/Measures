package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("7ab61d95-3946-4ae6-bfe0-3c615c662a3d")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("d1f115a9-5f72-48d0-a9f7-f96e0e3a3db5")
    public IntegerMeasurement() {
        
    }

    @objid ("2370738b-0f4c-4e70-843a-6c2322e3ef31")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("3276d115-d4ea-448b-a069-aef208d67ab0")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("917da312-f7a8-45d9-b757-7a56a87b62c3")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
