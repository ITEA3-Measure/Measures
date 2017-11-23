package org.measure.impl;

import java.util.ArrayList;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("41a78b50-873a-49c2-b4e8-969efbd85fc9")
public class DerivedMeasureImpl extends DerivedMeasure {
    @objid ("2c763edb-9691-4665-8fd9-8ffc5e1ca2c9")
    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        DerivedMeasureData data = new DerivedMeasureData(result);
        return result;
    }

}
