package org.measure.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("53df15f4-391d-4e55-a4d7-e2e138abd896")
public class MemoryUsage extends DirectMeasure {
    @objid ("539d21c4-d284-40f2-bc26-2358b1d412ef")
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
