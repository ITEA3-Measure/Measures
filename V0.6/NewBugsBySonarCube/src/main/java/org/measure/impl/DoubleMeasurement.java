package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("994b4916-318f-4bbc-ac18-61a1e8c347d3")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("1c698ed4-b0e8-4be2-9c1b-059c1f258278")
    public DoubleMeasurement() {
        
    }

    @objid ("2c4eb75e-dca8-4241-b460-d3d0681d1f53")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("aa22e8fa-cb95-4a43-b5c5-270a8c9a94a5")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("f8379450-4559-453c-86bc-3fcb40a40d74")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
