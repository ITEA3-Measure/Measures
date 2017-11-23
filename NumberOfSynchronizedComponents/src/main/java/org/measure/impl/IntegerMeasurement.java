package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("853c9174-fe07-429d-b84c-37e320ac7ecd")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("a75e777a-c56d-46cb-9998-8c25bfdf5393")
    public IntegerMeasurement() {
        
    }

    @objid ("dceb828a-7ed2-4e08-8201-0d8305652506")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("1f815e0d-d4af-49b5-977f-7a6070dd0df7")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("90ce5ab3-92f7-49e7-9706-1f0621524a47")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
