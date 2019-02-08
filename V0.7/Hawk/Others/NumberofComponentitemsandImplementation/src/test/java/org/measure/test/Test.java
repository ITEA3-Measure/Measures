package org.measure.test;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.DerivedMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("90d04a7d-383a-4ea4-9c57-7f647586f2d4")
public class Test {
    @objid ("c6e60fc0-d644-4f8a-9df4-3de01198d32f")
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
