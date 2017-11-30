package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("838e5996-d6a8-4c17-99b1-44a1a94eff62")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("dbddfedc-95e5-4ac9-8805-bba7c9b61bac")
    public DoubleMeasurement() {
        
    }

    @objid ("5de8a683-eccf-4a75-a91d-3cdbcc2174a5")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("8acbb3ca-ddfe-467d-a662-1571bf219fbe")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("1eed2b1d-f291-44c1-8221-47ab219ef557")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
