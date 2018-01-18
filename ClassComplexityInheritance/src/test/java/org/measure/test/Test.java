package org.measure.test;

import org.measure.impl.ClassComplexityInheritance;
import org.measure.smm.measure.api.IMeasurement;


public class Test {

    @org.junit.Test
    public void testMeasure() {
        ClassComplexityInheritance measure = new ClassComplexityInheritance();

        measure.getProperties().put("URL", "https://svn.softeam.fr/svn/MEASURE/trunk/Software/SMM_EMF_API");
        measure.getProperties().put("LOGIN","sdahab");
        measure.getProperties().put("PASSWORD","3bI2RE78m&");
        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Result : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
