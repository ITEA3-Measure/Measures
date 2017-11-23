package org.measure.impl;

import java.util.ArrayList;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("65ed6073-555f-469e-afec-562337e46914")
public class DerivedMeasureImpl extends DerivedMeasure {
    @objid ("64a50504-3c82-4575-aa79-60bb8448f87e")
    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        DerivedMeasureData data = new DerivedMeasureData(result);
        return result;
    }

}
