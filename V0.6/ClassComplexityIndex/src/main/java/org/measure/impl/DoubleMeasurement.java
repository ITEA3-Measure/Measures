package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("2eafc003-675a-4f96-b759-d699d54fbbf2")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("7eb77660-42cb-4a30-85da-d6774558194b")
    public DoubleMeasurement() {
        
    }

    @objid ("8df2bdd4-05c8-435d-9abd-e7860e5bbd02")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("58c4010b-0ca9-4d22-9b4c-dfa380a28950")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("1292d644-756a-4dc4-986c-4774519b6a07")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
