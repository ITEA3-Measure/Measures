package org.measure.impl;

import java.util.Date;
import java.util.HashMap;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("6ef82b03-6466-4249-b2fd-1d1a10a9a58a")
public class JVMCpuUsageMeasurement extends DefaultMeasurement {
    @objid ("bc2607b4-e482-4cfb-9705-a2e1d57253cc")
    public JVMCpuUsageMeasurement(String system, Double cpuusage) {
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

    @objid ("d8f2d9f5-a154-47c7-baa2-8a401ba0fdd3")
    @Override
    public String getLabel() {
        
        return valueMap.get("System") + " : " + valueMap.get("Usage");
    }

}
