package org.measure.test;

import org.measure.smm.measure.api.IMeasurement;

public class Test {

    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("ServerURL", "http://localhost:9000/");
        measure.getProperties().put("Login", "admin");
        measure.getProperties().put("Password", "admin");
        measure.getProperties().put("ProjectKey", "org.project:module.app");
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
