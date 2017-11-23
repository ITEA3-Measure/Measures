package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("502f9297-5e98-42be-9f22-60577edefadd")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("887b1c6f-8a48-4817-a119-77947e5fb489")
    public IntegerMeasurement() {
        
    }

    @objid ("762aa80b-d5aa-4d89-9041-5a3376542e54")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("91a63256-72af-4c53-a80d-882f4a33fb56")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("4add6f4f-e78d-47ba-b83a-e6cba743074a")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
