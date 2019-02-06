package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;

@objid ("ab0ff75f-fb2b-47d3-b863-ca7c86d01e1c")
public class Test {
    @objid ("30815b56-28f3-42d8-a0dc-de6b21779b66")
    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("openprojectUrl", "http://10.78.1.18/openproject");
        measure.getProperties().put("apikey", "0323be1d3840625c0a8b6fdd53480d0b03e4f4b357a01c0f17e3f61e3906227d");
        measure.getProperties().put("projectName", "Modelio 3.8");
        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Result : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
