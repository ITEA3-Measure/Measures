package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;

@objid ("277092ba-4856-42d8-a312-970aea4d20c7")
public class Test {
    @objid ("787e8e88-8442-46c0-8e04-c8e0c1078c76")
    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.SVNTrackerImpl measure = new org.measure.impl.SVNTrackerImpl();
        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
