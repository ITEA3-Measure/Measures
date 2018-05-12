package org.measure.impl;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("2f628e74-aa9b-4217-ad00-0151d88501d1")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("c1e5d167-165c-4d2e-912d-db98b82aaf4d")
    public DirectMeasureData(String project, String session_id, String test_id, Date execution_date, String type, String subject, String status, Long duration) {
        this.valueMap.put("project", project); 
        this.valueMap.put("session_id", session_id); 
        this.valueMap.put("test_id", test_id); 
        this.valueMap.put("execution_date", execution_date); 
        this.valueMap.put("type", type); 
        this.valueMap.put("subject", subject); 
        this.valueMap.put("status", status); 
        this.valueMap.put("duration", duration);
    }

}
