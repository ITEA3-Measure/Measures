package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;

public class DerivedMeasureImpl extends DerivedMeasure {

    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        DerivedMeasureData data = new DerivedMeasureData(result);
        return result;
    }

}
