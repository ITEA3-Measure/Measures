package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("c81faad5-c45d-45ab-bf36-dd190e3a933e")
public class DerivedMeasureImpl extends DerivedMeasure {
    @objid ("6541acfd-e8c1-4ea4-a23e-36dbd97a4fa9")
    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        DerivedMeasureData data = new DerivedMeasureData(result);
        return result;
    }

}
