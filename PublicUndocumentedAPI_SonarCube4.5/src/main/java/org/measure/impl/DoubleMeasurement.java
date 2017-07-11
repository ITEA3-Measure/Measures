package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("683f021c-d471-462a-8f08-6190186e0514")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("c929645d-dd22-401b-96e5-1f5ca8f344b3")
    public DoubleMeasurement() {
        
    }

    @objid ("9713129b-223c-4360-a72c-9818aaf4d815")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("c9876e32-0263-4d9d-beb6-877a9c7e9065")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("7bce963a-9e8e-4b3e-8f03-9e82f79add54")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
