package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("baa06769-3d73-4596-b37c-5785a37527c7")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("a9fa7e2d-7c18-4383-8aac-a5413f4c114e")
    public DoubleMeasurement() {
        
    }

    @objid ("71f8914a-d92e-4516-9a06-17b1ee799370")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("2e1f2e4f-78dd-4807-80b3-a772688b5a11")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("07125796-a9c8-4a66-81d6-1c91127478b4")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
