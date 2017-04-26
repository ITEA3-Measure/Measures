package org.measure.cpuusagemeasure;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import com.sun.management.OperatingSystemMXBean;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class CpuUsage extends DirectMeasure{

	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> result=new ArrayList<IMeasurement>();


		return result;
	}

	public static void main(String[] args){

        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        System.out.println(bean.getProcessCpuLoad());
        System.out.println(bean.getSystemCpuLoad());
        System.out.println(bean.getProcessCpuTime());
        System.out.println(bean.getTotalPhysicalMemorySize());
        System.out.println(bean.getArch());

    }

}
