package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8f0c74bc-7155-457a-8048-b73b6d0de521")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("9cf7ca22-a9ea-42b9-9da4-ab2e26df54c5")
    public DoubleMeasurement() {
        
    }

    @objid ("38cd0159-befd-4408-bdab-972e4df4275a")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("99353eeb-fe68-4def-85f3-94601fa46b3f")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("0c7aa22c-6855-432b-a224-0c6f31455e1d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
