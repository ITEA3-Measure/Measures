package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("c584ab45-7605-4316-aca6-45ba530bcbec")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("1ce7c384-ed56-4f21-b782-d375e95f1966")
    public DoubleMeasurement() {
        
    }

    @objid ("fa95002b-fe0f-4380-8b2c-339f123c3789")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("179afe4b-661d-4a88-9ef9-9306d54824d7")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("dd59f38b-fd83-4651-8132-243c05d4f450")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
