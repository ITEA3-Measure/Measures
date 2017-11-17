package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d295134a-97e6-45bd-95fe-2341d2d228c5")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("08be61c7-1f6f-4c91-b008-6def905e7633")
    public DoubleMeasurement() {
        
    }

    @objid ("39c87994-25f9-4293-bed7-63ec8d5b2d04")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("e9cb4d55-a202-49fd-9bff-9b8334c97f93")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("551a84e7-4cc0-40aa-8087-893dec91d5fa")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
