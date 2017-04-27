package org.measure.memoryusagemeasure;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.List;
import org.measure.memoryusagemeasure.MemoryUsageMeasurement;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class MemoryUsage extends DirectMeasure{

	@Override
	public List<IMeasurement> getMeasurement() throws Exception {
		List<IMeasurement> result=new ArrayList<IMeasurement>();
        MemoryMXBean memorybean=ManagementFactory.getMemoryMXBean();
        MemoryUsageMeasurement memoryHeap= new MemoryUsageMeasurement("HEAP", memorybean.getHeapMemoryUsage().getUsed());
        MemoryUsageMeasurement memoryJvm = new MemoryUsageMeasurement("JVM",memorybean.getNonHeapMemoryUsage().getUsed());
        result.add(memoryHeap);
        result.add(memoryJvm);
		return result;
	}

}
