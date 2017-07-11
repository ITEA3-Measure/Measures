package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("5a02b59f-f72f-49c9-a109-62352caacd73")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("30b97eb6-73f8-4166-ace6-ef1f5c95456b")
    public DoubleMeasurement() {
        
    }

    @objid ("314a3fcf-96f5-4d12-bc2f-9e3f926b1d69")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("46f54ce9-06c8-42c1-9aad-1d1a64ee2079")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("a37ee0c6-af5e-4b07-8c56-5f47aef23f97")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
