package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("0168ff95-1d99-4fb9-9956-03d105802c40")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("3f10e3f6-b607-4909-9e9d-8582c6aef5a1")
    public DoubleMeasurement() {
        
    }

    @objid ("9d626b73-306d-4c98-bec9-84db8427bb7f")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("f3554909-84a4-4b4d-bdf3-3b4d44c5cb80")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("8b35c49c-42bb-4efe-a508-065319796cae")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
