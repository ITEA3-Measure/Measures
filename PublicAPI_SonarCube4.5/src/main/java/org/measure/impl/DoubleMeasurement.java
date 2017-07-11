package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("332335de-bd4a-402a-9b6c-c302d093c4dd")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("ec0f8f68-a7f8-4afb-8be5-262e20c86acd")
    public DoubleMeasurement() {
        
    }

    @objid ("e2b56373-fcf6-4d09-b41c-afa03a063e31")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("13687541-706a-4a1b-9a04-9a4b30a34341")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("38494a6c-a1bd-469e-960e-6a906a91725e")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
