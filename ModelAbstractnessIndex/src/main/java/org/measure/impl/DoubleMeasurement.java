package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("877b6c10-17b6-4cc1-b6a5-641b0c045a41")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("a7c0f706-13c2-4bb9-add5-6593776b4fe7")
    public DoubleMeasurement() {
        
    }

    @objid ("7fc1f691-f978-4559-8b21-ac235ce1e955")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("c9ba69fa-44fa-4dfc-bcff-3e69472aee7f")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("a0d691a4-7439-409e-b171-96fd46fca623")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
