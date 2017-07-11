package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("74d6c800-6471-475b-ba55-dc84189cc898")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("8acf8ddd-f84a-4276-a900-0c16b148f60e")
    public DoubleMeasurement() {
        
    }

    @objid ("66586a31-ef28-41c8-8247-da02486f2e27")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("076508f2-c1b3-4e06-b036-d48f21b5ab2d")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("d3be39ce-f6a0-4f7a-b637-d966265ebf5d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
