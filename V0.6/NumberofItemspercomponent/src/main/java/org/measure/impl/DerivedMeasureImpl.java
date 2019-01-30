package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("183d9246-e36c-475b-b300-f73acc600f6d")
public class DerivedMeasureImpl extends DerivedMeasure {
    @objid ("a10d56d7-b393-4d7c-8fbb-59cafbd3f15f")
    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        DerivedMeasureData data = new DerivedMeasureData(result);
        return result;
    }

}
