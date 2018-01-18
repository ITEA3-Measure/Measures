package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.junit.Test;
import org.measure.impl.ClassComplexity;
import org.measure.smm.measure.api.IMeasurement;

public class TestClassComplexity {

    @Test
    public void testClassComplexity() {
            ClassComplexity measure = new ClassComplexity();
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
