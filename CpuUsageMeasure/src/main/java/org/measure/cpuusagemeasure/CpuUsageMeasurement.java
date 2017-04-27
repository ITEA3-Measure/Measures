package org.measure.cpuusagemeasure;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

import java.util.Date;
import java.util.HashMap;

public class CpuUsageMeasurement extends DefaultMeasurement {

    public CpuUsageMeasurement(String system, Double cpuusage) {
        super();
        this.valueMap = new HashMap<>();
        if (system == null) {
            system = "";
        }
        if (cpuusage == null) {
            cpuusage = new Double(-1);
        }
        this.valueMap.put("System", system);
        this.valueMap.put("Usage", cpuusage);

    }

    @Override
    public String getLabel() {
        return valueMap.get("System") + " : " + valueMap.get("Usage");
    }
}