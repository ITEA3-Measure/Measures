package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("4f09f7e9-ec1e-4ab2-87c9-0e87823c9992")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("c722cea3-d143-42f4-94f9-c6ad8ffa09d7")
    public DoubleMeasurement() {
        
    }

    @objid ("2e69f29b-f067-4161-adf3-46a49bc74105")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("98cf7a6d-f4b7-40c3-a1b4-291f40263dc9")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("1cec5c2c-ac5b-4528-910b-cc0a0d6fcff5")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
