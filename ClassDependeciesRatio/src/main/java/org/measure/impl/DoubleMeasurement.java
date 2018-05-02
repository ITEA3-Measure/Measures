package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("3152be65-94ad-4d2b-9021-390bcf373625")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("b1fdeed3-3204-4cb2-85fe-f0bf2ce439e9")
    public DoubleMeasurement() {
        
    }

    @objid ("9c67c3f7-82c9-4ec6-8337-e6dd64b933b2")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("4439c2f7-a98e-4211-8f93-82ba36ec88da")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("77834699-c4db-42a9-afbf-f7b56cb65a8b")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
