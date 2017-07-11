package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8016a5b3-6b2c-4386-8023-4c1c3beabdf2")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("54cbc26e-5d47-4a33-acf6-4f9966130168")
    public DoubleMeasurement() {
        
    }

    @objid ("2bd9b7c3-dc94-4d23-b79e-655e78b8daa7")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("ccb021bb-c9f2-4326-a04c-2893a3e7c520")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("1dc94b18-3bb5-452a-8c70-6926054fb954")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
