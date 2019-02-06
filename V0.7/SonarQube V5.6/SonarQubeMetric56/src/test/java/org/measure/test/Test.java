package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;

@objid ("908b3ee2-5cf7-4a3a-8775-62035340f501")
public class Test {
    @objid ("009299c5-92de-44f4-ada6-c28281de808b")
    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("ServerURL", "http://localhost:9000/");
        measure.getProperties().put("Login", "admin");
        measure.getProperties().put("Password", "admin");
        measure.getProperties().put("ProjectKey", "org.modelio:aggregator-modelio.app");
        measure.getProperties().put("Metric", "new_code_smells");
        
        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
