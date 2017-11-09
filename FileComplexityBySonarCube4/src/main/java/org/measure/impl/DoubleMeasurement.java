package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("90d87482-420f-4035-90de-f75b5969abd0")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("feef9776-2edd-48dc-883b-80f338b84d32")
    public DoubleMeasurement() {
        
    }

    @objid ("b66a804c-f728-411b-ad12-b8bea426fb77")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("78cdac30-b2b9-4cac-9ee8-a45fa1106ba4")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("2fa94496-d849-4097-877c-b612fd929892")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
