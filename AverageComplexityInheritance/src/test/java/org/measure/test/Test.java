package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.AverageComplexityInheritance;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;

public class Test {
    @org.junit.Test
    public void testMeasure() {
        AverageComplexityInheritance measure = new AverageComplexityInheritance();
        try {

            IntegerMeasurement ccimeasured=new IntegerMeasurement();
            ccimeasured.setValue(378);
            measure.addMeasureInput("ClassComplexityInheritance","ClassComplexityInheritance A",ccimeasured);

            measure.getProperties().put("URL", "https://svn.softeam.fr/svn/MEASURE/trunk/Software/SMM_EMF_API");
            measure.getProperties().put("LOGIN","sdahab");
            measure.getProperties().put("PASSWORD","3bI2RE78m&");

            for(IMeasurement result : measure.calculateMeasurement() ){
                System.out.println("Test Result : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
