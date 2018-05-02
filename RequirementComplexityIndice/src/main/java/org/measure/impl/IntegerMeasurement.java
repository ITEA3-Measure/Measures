package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("14fd274f-ada0-46dc-be63-360a3576d68b")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("88720269-5695-471e-85ea-cb7333c687e3")
    public IntegerMeasurement() {
        
    }

    @objid ("911df915-e00f-48f6-ba71-3dd44aa50635")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("b33bdc02-7d7f-4a8f-aaf9-7ce21fa196c9")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("2f5707b3-a824-4d0f-81d6-06c92c775761")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
