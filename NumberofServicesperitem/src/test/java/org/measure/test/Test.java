package org.measure.test;

import org.measure.impl.DerivedMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("65b4d3a3-177a-40f4-825f-8567532be68c")
public class Test {
    @objid ("dd1339c8-d4b0-481f-9be0-ca4907cdb95f")
    @org.junit.Test
    public void testMeasure() {
        DerivedMeasureImpl measure = new DerivedMeasureImpl();
        try {
                        
            
            
            DefaultMeasurement input = new DefaultMeasurement();
            
            // TODO : Define Inputs
            // input.addValue("value", "...");
            
            measure.addMeasureInput("MeasureA", "RoleA", input);
            
            for(IMeasurement result : measure.calculateMeasurement() ){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
