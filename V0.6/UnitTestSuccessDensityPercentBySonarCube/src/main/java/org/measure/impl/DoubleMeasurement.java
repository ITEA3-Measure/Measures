package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("aa8de70f-82c2-41d7-9fab-aba27afd65af")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("0cef37b6-a87a-4e33-88b9-bcfd2dc0e7e9")
    public DoubleMeasurement() {
        
    }

    @objid ("9b93bbbd-d3ce-4f25-921c-2810dde5348a")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("95165b2f-fb2d-4fd7-bd04-0e1bed7541e3")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("6c475be9-5a26-4f84-bf9d-3fa04a016c61")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
