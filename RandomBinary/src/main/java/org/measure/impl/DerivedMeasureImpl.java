package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("83879811-dad1-41a3-8117-fa4f71b3e940")
public class DerivedMeasureImpl extends DerivedMeasure {
    @objid ("2c71563f-b639-4606-a7f7-b22039f8aae8")
    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        Integer result = 0;
        
        List<IMeasurement> op1 = getMeasureInputByRole("Random A");
        List<IMeasurement> op2 = getMeasureInputByRole("Random B");
            
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
