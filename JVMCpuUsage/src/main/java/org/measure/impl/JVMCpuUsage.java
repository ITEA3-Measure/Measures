package org.measure.impl;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import com.sun.management.OperatingSystemMXBean;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("1bdbbad4-b91a-40dd-938a-c65a98091cf3")
public class JVMCpuUsage extends DirectMeasure {
    @objid ("d018ab0d-30b2-431f-8fab-0ec244d881df")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result=new ArrayList<IMeasurement>();
        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        JVMCpuUsageMeasurement cpuJvm= new JVMCpuUsageMeasurement("JVM", bean.getProcessCpuLoad());
        JVMCpuUsageMeasurement cpuSystem = new JVMCpuUsageMeasurement("System",bean.getSystemCpuLoad());
        result.add(cpuJvm);
        result.add(cpuSystem);
        return result;
    }

}
