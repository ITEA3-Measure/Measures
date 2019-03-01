#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.ArrayList;
import java.util.List;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;

public class DerivedMeasureImpl extends DerivedMeasure {

    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        Integer result = 0;
        
        List<IMeasurement> op1 = getMeasureInputByRole("Role A");
        List<IMeasurement> op2 = getMeasureInputByRole("Role B");
            
        if(op1.size() == 1 && op2.size() == 1){            
            String oper = "+";    
            oper = getProperty("Operation");    
            
            Integer val1 =   (Integer) op1.get(0).getValues().get("value");
            Integer val2 =   (Integer) op2.get(0).getValues().get("value");
        
            if(oper.equals("+")){
                result = val1 + val2;
            }else if(oper.equals("-")){
                result = val1 - val2;
            }else if(oper.equals("*")){
                result = val1 * val2;
            }else if(oper.equals("/")){
                result = val1 / val2;
            }    
        }
        
        
        IntegerMeasurement measurement = new IntegerMeasurement();
        measurement.setValue(result);
        
        List<IMeasurement> measurements = new ArrayList<>();
        measurements.add(measurement);
        return measurements;
    }

}
