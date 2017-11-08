package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("4727c604-3410-44cb-9f6a-62f910e0b4ca")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("95a739a9-5efa-4c4a-a24f-09772a22010f")
    public DoubleMeasurement() {
        
    }

    @objid ("a9fe69e8-1e19-4176-8664-448fa09dd3a1")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("910de405-5a22-422f-bcf0-968df3fe01ba")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("23111820-cc2b-4ef2-b16b-13d9fe8fc13f")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
