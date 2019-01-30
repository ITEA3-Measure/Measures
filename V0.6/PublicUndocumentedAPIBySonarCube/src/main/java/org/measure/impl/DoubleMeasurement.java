package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("683f021c-d471-462a-8f08-6190186e0514")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("67eec651-1eef-4f3e-8f18-620f330bf73d")
    public DoubleMeasurement() {
        
    }

    @objid ("ceac4f9c-3e19-4804-8ff3-ccdfd732436d")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("ec93cb84-4846-4a8d-98bf-ce11d3ee14fe")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("7433f58d-3991-4a3a-b2e4-c1c3fe447adc")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
