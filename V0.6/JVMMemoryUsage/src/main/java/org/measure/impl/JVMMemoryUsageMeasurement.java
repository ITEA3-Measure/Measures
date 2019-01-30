package org.measure.impl;

import java.util.Date;
import java.util.HashMap;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("441cfb39-5a5d-44af-8a8b-0082341ac9cd")
public class JVMMemoryUsageMeasurement extends DefaultMeasurement {
    @objid ("8dea38be-e351-4ac8-9b09-8a1ebdd5fe6d")
    public JVMMemoryUsageMeasurement(String system, Long memoryusage) {
        super();
        this.valueMap = new HashMap<>();
        if (system == null) {
            system = "";
        }
        if (memoryusage == null) {
            memoryusage= new Long(-1);
        }
        this.valueMap.put("System", system);
        this.valueMap.put("Usage", memoryusage);
    }

    @objid ("4e3ef8b2-736a-4215-94d6-f01084267537")
    @Override
    public String getLabel() {
        
        return valueMap.get("System") + " : " + valueMap.get("Usage");
    }

}
