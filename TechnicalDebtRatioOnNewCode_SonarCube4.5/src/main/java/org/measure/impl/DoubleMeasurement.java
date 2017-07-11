package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("21dbb804-6965-4f03-8219-aae16fa8a559")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("34a94474-a03c-477d-8a10-ee37f473be3e")
    public DoubleMeasurement() {
        
    }

    @objid ("d7f06a62-42da-4337-9002-978658d85a15")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("d45a0f60-5012-49d4-a1ab-25a957701dc2")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("fac5dbab-86a2-498a-b3bc-3b96893502fa")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
