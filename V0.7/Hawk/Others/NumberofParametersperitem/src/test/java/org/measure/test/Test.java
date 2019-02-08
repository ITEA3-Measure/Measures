package org.measure.test;

import org.measure.impl.DerivedMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class Test {

    @org.junit.Test
    public void testMeasure() {
        DerivedMeasureImpl measure = new DerivedMeasureImpl();
        try {
                        
            
            
            DefaultMeasurement input = new DefaultMeasurement();
            
            // TODO : Define Inputs
            // input.addValue("value", "...");
            
            measure.addMeasureInput("MeasureA", "RoleA", input);
            
            for(IMeasurement result : measure.calculateMeasurement() ){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
