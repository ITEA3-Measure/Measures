package org.measure.memoryusagemeasure;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

import java.util.Date;
import java.util.HashMap;

public class MemoryUsageMeasurement extends DefaultMeasurement {

    public MemoryUsageMeasurement(String system, Long memoryusage) {
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

    @Override
    public String getLabel() {
        return valueMap.get("System") + " : " + valueMap.get("Usage");
    }
}