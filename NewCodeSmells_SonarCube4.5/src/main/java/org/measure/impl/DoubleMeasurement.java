package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("b26053a8-e669-4c71-89ea-3e2b8f1b8d14")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("50af5456-9cf9-4dd0-b973-76922b9814c2")
    public DoubleMeasurement() {
        
    }

    @objid ("4640b439-043c-42c4-9187-3fdd22fc8473")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("07ecc488-481f-49ae-bdad-79a37cd98418")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("2b8dbc30-5da4-4e79-b55f-0c1084dbcdcf")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
