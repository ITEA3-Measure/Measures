package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("c488a256-0cf4-42e1-b600-604ceb20bc72")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("09ca3b52-a20c-4191-b9a9-e911e6013253")
    public IntegerMeasurement() {
        
    }

    @objid ("650b6917-2865-4081-8aa0-a8c9c3a4c566")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("972def2e-3dcb-49d8-97c8-423c03a41cfa")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("e5ef5afb-460c-49ef-bee9-ad9b8fe84857")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
