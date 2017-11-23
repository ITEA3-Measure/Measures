package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("7eb46594-f779-4b01-85a3-15f7ebf7dfe7")
public class IntegerMeasurement extends DefaultMeasurement {
    @objid ("19fd64b8-0987-4d08-a7d4-6bc6ddd7770f")
    public IntegerMeasurement() {
        
    }

    @objid ("198797ac-f338-4fb2-aba6-80d0b1a5477c")
    public void setValue(Integer value) {
        addValue("value",value);
    }

    @objid ("bbb51a86-9c73-48a0-bf58-408d896aa40b")
    public Integer getValue() {
        
        return (Integer) getValues().get("value");
    }

    @objid ("d00b200b-db96-4930-980a-a254f2359058")
    @Override
    public String getLabel() {
        
        return getValues().get("value").toString();
    }

}
