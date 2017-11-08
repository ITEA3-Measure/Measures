package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("5ca57b05-d8e6-482f-b234-5e3e299a05d4")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("20650322-7f80-48c3-9e74-273efdfe470e")
    public DoubleMeasurement() {
        
    }

    @objid ("4dce60ba-0030-40ef-a4cc-26bfc656a866")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("03d2b1f8-0613-45fa-b24f-404a26f6f202")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("c0fe8090-c803-4634-9bc3-28a28d51fffb")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
