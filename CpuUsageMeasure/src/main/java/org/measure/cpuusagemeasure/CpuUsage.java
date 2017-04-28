package org.measure.cpuusagemeasure;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import com.sun.management.OperatingSystemMXBean;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;


public class CpuUsage extends DirectMeasure{

	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> result=new ArrayList<IMeasurement>();
        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        CpuUsageMeasurement cpuJvm= new CpuUsageMeasurement("JVM", bean.getProcessCpuLoad());
        CpuUsageMeasurement cpuSystem = new CpuUsageMeasurement("System",bean.getSystemCpuLoad());
        result.add(cpuJvm);
        result.add(cpuSystem);
		return result;
	}

}
