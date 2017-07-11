package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("584c4973-bb05-41ed-817c-d7e986e24a0c")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("83b4c41c-b488-4f5e-b820-5e752b299d74")
    public DoubleMeasurement() {
        
    }

    @objid ("77df3327-c6cc-4ad5-81fb-7d3e59e1b769")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("48a33326-c4cf-47ee-b8af-544b12f6ba9c")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("02e98053-5df1-470f-942f-eccc8b7008e5")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
