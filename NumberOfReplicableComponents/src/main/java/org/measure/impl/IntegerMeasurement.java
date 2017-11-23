package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("c4f04244-70dc-4616-aa1a-3968b7470543")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("6a1e5efb-2a55-4c65-af58-61393b1c3cd8")
    public IntegerMeasurement() {
        
    }

    @objid ("b6b3f6d2-c51e-4725-b40b-b23e951a7c7b")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("e738c615-a3b8-43c2-aede-af4a38f3a99f")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("4c20b589-aa24-49c6-9a70-77b3b2f96e2b")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
