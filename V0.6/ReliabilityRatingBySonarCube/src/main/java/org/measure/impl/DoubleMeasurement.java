package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("4245b1e4-2389-492a-a6f7-5eeed7c175e8")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("6fa99ec4-dfdd-4617-ae6f-5097e095a469")
    public DoubleMeasurement() {
        
    }

    @objid ("fa2d8bd6-5fa7-4f78-83d1-60129cb66156")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("11130aba-4ec4-493a-a6a2-9b4dd0696d61")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("678ba886-e706-4bed-9ce1-9bc0536b08d3")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
