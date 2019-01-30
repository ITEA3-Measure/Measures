package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("41de5400-380e-4306-85a6-3a8fae2e1807")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("a8405a17-b113-4e94-b4b1-3c5ee1426c69")
    public IntegerMeasurement() {
        
    }

    @objid ("dcc15dd2-cba5-473c-acac-8a69450bc1f3")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("647b30de-68d7-4f9f-8ebe-66e2513874d9")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("6ad0a5b8-8105-4efd-8892-ecdeaf58a22c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
