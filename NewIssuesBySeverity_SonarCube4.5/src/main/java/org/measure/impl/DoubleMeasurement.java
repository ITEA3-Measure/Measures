package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8f0c74bc-7155-457a-8048-b73b6d0de521")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("5b12d072-f29d-4171-a6fc-33b108f72119")
    public DoubleMeasurement() {
        
    }

    @objid ("c603fc01-6d54-4c80-840f-b10b073e70be")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("e754aae4-96c3-483e-916a-3df8819d17d8")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("cd5908d6-6c55-4159-9111-be8c21c8add4")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
