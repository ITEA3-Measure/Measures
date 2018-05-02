package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("56359a11-b4af-490e-80a8-04107cd82cbf")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("0d236db3-19d5-412a-85c2-e07d8e3d56b4")
    public IntegerMeasurement() {
        
    }

    @objid ("31c4bbbb-6aa5-4c73-bec7-2a12cce37c6f")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("619c97d9-bfb9-4277-9fdc-e489e72839e0")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("9ad5574c-87f7-484b-8794-9a185457423f")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
