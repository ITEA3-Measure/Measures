package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("dc79a81e-d72b-4023-81e8-9111af1f0d47")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("878f7b80-0226-4f1c-b994-8f996c833a61")
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
