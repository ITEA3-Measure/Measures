package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class DirectMeasureImpl extends DirectMeasure {

    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // TODO : Read Scope Properties
        int maxRange =  Integer.valueOf(getProperty("MaxRange"));
        int minRange = Integer.valueOf(getProperty("MinRange"));
        int delta = Integer.valueOf(getProperty("Delta"));
        int previousValue = Integer.valueOf(getProperty("Value"));
        
        // TODO : Get Measurement
        Random gen = new Random();
        int value = gen.nextInt(delta * 2) - delta + previousValue;
        
        if(value < minRange){
            value = minRange;
        }
        
        if(value > maxRange){
            value = maxRange;
        }
        
        DirectMeasureData data = new DirectMeasureData();
        
        // TODO : Set Return data
        data.setValue(value);    
        result.add(data);
        
        getProperties().put("Value", String.valueOf(value));
        return result;
    }

}
