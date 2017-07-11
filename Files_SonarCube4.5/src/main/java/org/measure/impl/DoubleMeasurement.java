package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("71ed6b89-0959-412d-9704-53a088cf03ba")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("757ada73-4df6-4d00-b0bf-99536dc9869f")
    public DoubleMeasurement() {
        
    }

    @objid ("981f6c22-1ff5-40e8-aca6-9e81de13ce76")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("b927415a-5af4-4920-ac19-e143a507b1d9")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("cb473598-1a5e-4cb3-90b7-7e1e0c7ab7fa")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
