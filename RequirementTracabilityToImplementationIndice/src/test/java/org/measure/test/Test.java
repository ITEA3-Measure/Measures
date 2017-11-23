package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.DirectMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;

@objid ("39bddcce-a431-47e4-8d0a-85b536636db2")
public class Test {
    @objid ("83422eb5-e12b-49b6-bec0-4612f014da25")
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
