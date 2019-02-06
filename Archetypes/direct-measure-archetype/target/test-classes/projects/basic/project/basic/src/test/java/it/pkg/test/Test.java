package it.pkg.test;

import org.measure.smm.measure.api.IMeasurement;


public class Test {

    @org.junit.Test
    public void testMeasure() {
        it.pkg.DirectMeasureImpl measure = new it.pkg.DirectMeasureImpl();
        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
