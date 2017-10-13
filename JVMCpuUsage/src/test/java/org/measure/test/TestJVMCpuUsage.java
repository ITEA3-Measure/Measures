package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.junit.Test;
import org.measure.impl.JVMCpuUsage;
import org.measure.smm.measure.api.IMeasurement;

@objid ("aa94dae8-83a7-4d54-9282-9ee96c829271")
public class TestJVMCpuUsage {
    @objid ("cc56a426-ca92-497e-bd32-c74405dfa992")
    @Test
    public void testJVMCpuUsage() {
        JVMCpuUsage measure = new JVMCpuUsage();
        measure.getProperties().put("Property1", "Value1");



        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Result : " + result.getLabel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
