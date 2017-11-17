package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("f19ad1a5-81d8-4b58-8150-ad25e5dde9af")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("1fd1ecc3-4951-4401-99dc-a293464cc2ca")
    public DoubleMeasurement() {
        
    }

    @objid ("32ad14a3-a11f-462e-98db-46f2fc79162e")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("8f857326-8fd2-477a-a304-133332886c6f")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("7174204b-0152-4877-aba3-bd767e8e4737")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
