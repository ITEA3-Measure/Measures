package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.junit.Test;
import org.measure.impl.WeightedClassComplexity;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;

@objid ("4b01b2ad-f0bd-4b88-aeed-c97022de6966")
public class TestWeightedClassComplexity {
    @objid ("c564728e-7816-463d-a72d-0d99de76a3f0")
    @Test
    public void testWeightedClassComplexity() {
        WeightedClassComplexity measure = new WeightedClassComplexity();
        measure.getProperties().put("URL", "https://svn.softeam.fr/svn/MEASURE/trunk/Software/SMM_EMF_API");
        measure.getProperties().put("LOGIN","sdahab");
        measure.getProperties().put("PASSWORD","3bI2RE78m&");
        //IntegerMeasurement weightmeasured=new IntegerMeasurement();
        //weightmeasured.setValue(1542);
        //measure.addMeasureInput("Class Complexity","ClassComplexity A",weightmeasured);
        try {
            for(IMeasurement result : measure.calculateMeasurement()){

                System.out.println("Test Result : " + result.getLabel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
