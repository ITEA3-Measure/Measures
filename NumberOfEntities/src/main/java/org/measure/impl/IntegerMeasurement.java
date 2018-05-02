package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("2c507b0e-ed20-44d0-8593-a31101a4f7e1")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("cf5883bd-8253-41f4-8d84-20d12c23742a")
    public IntegerMeasurement() {
        
    }

    @objid ("5dfad7e5-214d-4ac7-a908-1ac177526ebc")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("a23a419d-4f46-494f-a454-126af8c23a5b")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("0a390a66-a4ff-4030-996c-8bfda059234e")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
