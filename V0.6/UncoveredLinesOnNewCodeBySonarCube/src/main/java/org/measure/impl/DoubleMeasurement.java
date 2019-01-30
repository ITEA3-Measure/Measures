package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("e69e8c30-e377-402d-b5ef-e40f6cc219fb")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("728ddf79-353d-4fba-a434-4d11363e5721")
    public DoubleMeasurement() {
        
    }

    @objid ("d279b308-4602-4e25-bc43-28b0831b0a53")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("fcb42b01-0cc4-4a53-85c3-9fbca222587b")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("c5ddf753-550c-4cff-8cfd-8996867bca0c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
