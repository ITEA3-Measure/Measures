#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import org.measure.smm.measure.api.IMeasurement;



public class Test {

	@org.junit.Test
	public void testMeasure() {
		${package}.DerivedMeasureImpl measure = new ${package}.DerivedMeasureImpl();

		measure.getProperties().put("url", "localhost");

		try {
			for(IMeasurement result : measure.calculateMeasurement()){
				System.out.println("Test Result : " + result.getLabel());
			}            
		} catch (Exception e) {
			e.printStackTrace();
		}        

	}

}
