package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.DirectMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;

@objid ("420968e7-916f-4e5f-a3b9-1b273d51cdc1")
public class Test {
    @objid ("f46095e6-d9b1-4ab5-b7a9-da51ab1e8fe2")
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
