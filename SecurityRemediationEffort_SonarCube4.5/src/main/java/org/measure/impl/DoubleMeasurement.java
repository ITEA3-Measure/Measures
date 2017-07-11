package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("dd7f2235-3526-44f3-807c-3d2abea3d2d6")
public class DoubleMeasurement extends DefaultMeasurement {
    @objid ("2cfbfedd-b9db-469a-bdfc-dfbf7249146c")
    public DoubleMeasurement() {
        
    }

    @objid ("3b9b8137-2935-49af-8933-8d948378b25a")
    public void setValue(Double value) {
        addValue("value",value);
    }

    @objid ("293373f4-d8c5-47e8-b5f7-c66c0045315d")
    public Double getValue() {
        
        return (Double) getValues().get("value");
    }

    @objid ("e4bc5e2e-8166-4995-a39e-46cba690d08d")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
