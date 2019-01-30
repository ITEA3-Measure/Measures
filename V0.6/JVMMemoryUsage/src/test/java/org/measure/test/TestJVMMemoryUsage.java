package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.junit.Test;
import org.measure.impl.JVMMemoryUsage;
import org.measure.smm.measure.api.IMeasurement;

@objid ("af40712a-68e5-4505-9adf-92556756f13d")
public class TestJVMMemoryUsage {
    @objid ("53066ab9-4cf3-4d55-89a4-ffcbd4c6427b")
    @Test
    public void testJVMMemoryUsage() {
        JVMMemoryUsage measure = new JVMMemoryUsage();

        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Result : " + result.getLabel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
