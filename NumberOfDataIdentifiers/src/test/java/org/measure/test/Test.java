package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.DirectMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;

@objid ("9beb1ae4-735c-40af-a706-36da4930e2a7")
public class Test {
    @objid ("a3a8119b-26cb-4806-9b60-d9e66165dd1b")
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
