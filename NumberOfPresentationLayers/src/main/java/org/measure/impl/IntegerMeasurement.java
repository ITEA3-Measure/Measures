package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("95359edc-0fb2-4b07-ba7c-4f3aab012022")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("d529e879-8cce-485e-87b6-80414163d5b2")
    public IntegerMeasurement() {
        
    }

    @objid ("1235c43f-c57c-4844-8d38-05a8aae60b0c")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("dba98f12-9601-4567-afeb-bbc5955413f0")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("d92be80a-bf83-4022-9890-15904dbe1853")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
