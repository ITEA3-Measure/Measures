package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("9f27c502-4eb0-44f1-9a13-023055a312f3")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("00d767e7-6117-4acf-8c52-374dbe3b55df")
    public DoubleMeasurement() {
        
    }

    @objid ("e00bf6de-3b83-4e58-99c9-41648c016e70")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("6162c583-2af5-43ea-b38b-c2ee70a354b6")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("0a3b4329-80d0-4e01-87b7-2ae7a1ed7f63")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
