#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import org.measure.smm.measure.api.IMeasurement;


public class Test {

    @org.junit.Test
    public void testMeasure() {
        ${package}.DirectMeasureImpl measure = new ${package}.DirectMeasureImpl();
        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Resul : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
