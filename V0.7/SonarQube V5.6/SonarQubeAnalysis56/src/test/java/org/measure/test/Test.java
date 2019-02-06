package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;

@objid ("39d8154e-b378-481b-b806-a89b231171dd")
public class Test {
    @objid ("a469473f-2abe-4dbb-8d06-294349d6f3d5")
    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("ServerURL", "http://localhost:9000/");
        measure.getProperties().put("Login", "admin");
        measure.getProperties().put("Password", "admin");
        measure.getProperties().put("ProjectKey", "org.modelio:aggregator-modelio.app");
        measure.getProperties().put("Metrics", "new_code_smells,bugs,code_smells");
        
        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
