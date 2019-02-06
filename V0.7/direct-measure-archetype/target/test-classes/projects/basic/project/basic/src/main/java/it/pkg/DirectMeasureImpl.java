package it.pkg;

import java.util.ArrayList;
import java.util.List;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class DirectMeasureImpl extends DirectMeasure {

    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // TODO : Read Scope Properties
        
        // TODO : Get Measurement
        
        DirectMeasureData data = new DirectMeasureData();
        
        // TODO : Set Return data
        
        result.add(data);
        return result;
    }

}
