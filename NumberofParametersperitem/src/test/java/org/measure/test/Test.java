package org.measure.test;

import org.measure.impl.DerivedMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("d353abca-b292-4d57-a167-98803ea62afa")
public class Test {
    @objid ("a152112f-eb3b-408e-8632-475f60f3c605")
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
