package org.measure.test;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.DirectMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;

@objid ("e2b883b5-de90-429d-874f-3b8fa062a165")
public class Test {
    @objid ("c741f97d-35b3-4ff9-9677-decc53879c31")
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
