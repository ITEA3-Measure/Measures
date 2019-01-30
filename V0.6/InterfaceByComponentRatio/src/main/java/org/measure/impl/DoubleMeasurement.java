package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("c768b870-32dc-415c-9b6a-6917bd345f18")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("52db3ad1-b7ae-4636-ba44-8dc73bbffdb5")
    public DoubleMeasurement() {
        
    }

    @objid ("452bed79-c9e6-4696-ba75-e99827750567")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("95db87ed-5475-4186-ac77-e7f9b939ba01")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("3ba4e5ff-17a3-4c9a-8a87-8621cd7b3f4c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
