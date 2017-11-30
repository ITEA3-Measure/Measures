package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("91efb0f9-f87d-4d33-960a-b4ee5bdf7fae")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("6fce6a9b-61e9-48e0-bfdc-cdf446519e3c")
    public DoubleMeasurement() {
        
    }

    @objid ("eda346b1-0e7b-49a5-b8bf-872e94fea261")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("0f0c092a-efee-47ab-b10d-37054acca8cf")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("11f44df1-fd9d-4872-adf9-1dc40c820b3c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
