package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("ebd89570-d58d-44d9-ae69-f3fd4a462f57")
public class DerivedMeasureImpl extends DerivedMeasure {
    @objid ("0941ace9-7445-423c-95a6-c2829eba9f34")
    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        DerivedMeasureData data = new DerivedMeasureData(result);
        return result;
    }

}
