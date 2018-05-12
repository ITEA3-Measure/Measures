package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("653a5495-efff-4452-891b-b50833a5ceb4")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("13c69d9f-090e-4d14-b66a-7554acf5a6f3")
    public IntegerMeasurement() {
        
    }

    @objid ("81608ef4-ae41-4f25-ac1f-92e2ca757256")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("e4219e85-923c-488e-8d30-33e969c42748")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("0a601cd4-f173-4b28-b163-e765d36070f6")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
