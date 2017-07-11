package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("994b4916-318f-4bbc-ac18-61a1e8c347d3")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("0a1eb3c0-37ba-44e0-9161-a71209b1df42")
    public DoubleMeasurement() {
        
    }

    @objid ("1225157d-3653-47a6-af26-7f877b027b40")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("604498e8-c335-4bc8-a1d3-210417525f80")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("4657b8a5-a02e-4c71-b490-3f592c7a3e5f")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
