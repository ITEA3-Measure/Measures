package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.ExtendedWeightedClassComplexity;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;

@objid ("89f4daf3-356d-43ad-a3f6-5d948ed7ffed")
public class Test {
    @objid ("0fae1ea2-9926-4ca8-80d1-27355f7a602a")
    @org.junit.Test
    public void testMeasure() {

        try {
            ExtendedWeightedClassComplexity measure = new ExtendedWeightedClassComplexity();

            IntegerMeasurement wccmeasured=new IntegerMeasurement();
            wccmeasured.setValue(2269);
            measure.addMeasureInput("WeightedClassComplexity","WeightedClassComplexity A",wccmeasured);

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
