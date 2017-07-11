package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("91efb0f9-f87d-4d33-960a-b4ee5bdf7fae")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("05344e0d-1e66-46d9-9d06-245dfa788b4a")
    public DoubleMeasurement() {
        
    }

    @objid ("b0919ad8-fd64-4652-8c40-209ac3338615")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("45ddbec3-e563-462d-af7f-29fcbdc521b9")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("9ff9adef-e9f4-4a29-aa04-6782a01aee5b")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
