package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("b6e8ecd3-3d55-40bf-985a-325c8accc6ef")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("bc57808b-d9d2-4e8a-8a47-802cb04208f6")
    public IntegerMeasurement() {
        
    }

    @objid ("7d4359b5-cdb7-4054-a163-87c86e6f1a33")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("113bfad5-7dc9-4425-85f6-8b41cc0fbfff")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("5118338d-0ee3-4b5e-a4ce-f6f65d85fd5c")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
