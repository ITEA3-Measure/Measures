package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("0652e359-8d9c-4a98-a0a4-264bb9909e28")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("1dd9fc9e-86bf-4507-aaa0-2816e53e71a7")
    public IntegerMeasurement() {
        
    }

    @objid ("70d3ef14-1237-4942-9f92-21cf00b1409d")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("5ba71dd5-84d1-440b-903e-63890cceed58")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("97ad680e-f221-455b-a7ff-710693d1a91e")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
