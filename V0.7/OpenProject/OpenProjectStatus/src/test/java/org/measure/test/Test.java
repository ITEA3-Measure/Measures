package org.measure.test;

import org.measure.smm.measure.api.IMeasurement;

public class Test {
    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("openprojectUrl", "localhost/openproject");
        measure.getProperties().put("apikey", "apikey1");
        measure.getProperties().put("projectName", "projectName");
        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
