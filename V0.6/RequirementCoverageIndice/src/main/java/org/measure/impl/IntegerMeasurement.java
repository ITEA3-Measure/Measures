package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("0fefdf80-2604-4fb1-b1df-fd11a98659ea")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("b6ed0c84-5748-45f5-b3ba-75a727dca5c6")
    public IntegerMeasurement() {
        
    }

    @objid ("ae5d5932-9ef0-4fb5-a9ce-ad26b38b1dfc")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("96b39885-3dd0-47fb-b795-2d8f0c3b5ba2")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("b8979952-d4ee-4a9b-b609-6105dbe13d6c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
