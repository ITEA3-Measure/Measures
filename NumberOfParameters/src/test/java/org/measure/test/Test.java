package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.DirectMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;

@objid ("5b1c7c7c-0f54-4a4e-ac1f-a2aad8615f18")
public class Test {
    @objid ("058b1578-019a-42c3-9aad-7dc4428d6c2d")
    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        try {
            
            
            measure.getProperties().put(DirectMeasureImpl.SCOPE_SERVERURL, "http://localhost:8080/thrift/hawk/tuple");
            measure.getProperties().put(DirectMeasureImpl.SCOPE_REPOSITORY, "*");
            measure.getProperties().put(DirectMeasureImpl.SCOPE_INSTANCENAME, "instance_37");
            
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
