package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8f2a2af7-f529-48de-bf0a-63778c95d997")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("a2fdbaba-f310-4c1f-a52e-bc784108342e")
    public DoubleMeasurement() {
        
    }

    @objid ("b39ef831-a989-4e3e-8587-d73f771be9a2")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("004d82d6-ab77-4ccc-82fd-f83e040b6ce8")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("90fc2240-8273-472a-a963-0010e4434106")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
