package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("2b961398-61cc-437a-8145-bbac41b224ec")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("6aae3642-990d-4e0e-90e1-82d3bcbe95ef")
    public IntegerMeasurement() {
        
    }

    @objid ("e2d20908-c8fd-42c7-899a-8f26e8fec672")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("c5ddde64-dad6-4a2d-95d1-8e2d36259ab5")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("4e2f9c9f-3acc-4c74-85de-e8de5f09ac0f")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
