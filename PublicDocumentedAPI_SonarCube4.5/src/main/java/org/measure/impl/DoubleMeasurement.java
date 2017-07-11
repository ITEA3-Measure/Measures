package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("1c6ed25d-f5bc-4b73-9458-366ed04aef2b")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("9f6d8392-2815-48fc-8e5a-f7dc30df10b6")
    public DoubleMeasurement() {
        
    }

    @objid ("89bd718b-1738-445f-825b-58ab3e4d8368")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("065dd425-fcff-4f29-993a-b81262b75607")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("4ccfd5a5-05b1-4ce9-b9b7-d936fe6c8270")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
