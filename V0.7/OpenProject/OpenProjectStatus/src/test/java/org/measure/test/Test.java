package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;

@objid ("a1a9e3e9-1003-4c3d-a492-e4228ba057cf")
public class Test {
    @objid ("35e2dc88-50d9-4d4e-a04f-f014c49e21a1")
    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("openprojectUrl", "http://10.78.1.18/openproject");
        measure.getProperties().put("apikey", "03bb71478de3e872cb0778d0c2fc473b08ac3075090fc33213eb18e4c0e166ef");
        measure.getProperties().put("projectName", "Modelio 3.8");
        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
