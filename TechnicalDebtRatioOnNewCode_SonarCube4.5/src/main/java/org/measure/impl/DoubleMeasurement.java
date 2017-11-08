package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("21dbb804-6965-4f03-8219-aae16fa8a559")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("908a7863-77c3-420b-b16f-fbd39016ae7d")
    public DoubleMeasurement() {
        
    }

    @objid ("cb9128b8-8d3d-4e41-99e8-b48eb63f6931")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("cd7b9962-0319-4ed9-8bed-29efba2737c1")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("618d7787-889a-4f6b-925e-068a1154ad84")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
