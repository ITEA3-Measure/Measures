package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("baa06769-3d73-4596-b37c-5785a37527c7")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("46e242f8-2dc7-41fe-871b-a11b00efcd42")
    public DoubleMeasurement() {
        
    }

    @objid ("13fb0a9a-b8b6-4419-a402-55f5b13f3e40")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("b331e6a0-5883-4504-a92a-ef0078034516")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("269d53da-9aa0-4036-9bc7-4f5ea217f178")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
