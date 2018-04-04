package org.measure.test;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.DerivedMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("9bb268cb-008e-4be0-bf72-642f4cc7eb17")
public class Test {
    @objid ("033eb10d-beae-4fcb-93f0-d72751aa533f")
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
