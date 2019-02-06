package org.measure.impl;

import java.util.Date;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8f90c7f0-abf6-4a22-97be-27b43f029846")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("00c1583f-ad72-48f0-a146-742e77c33d4f")
    public DirectMeasureData(Date date) {
        getValues().put("postDate", date);
    }

    @objid ("144be3f4-2a8a-4601-916d-469500420bc0")
    public void addValue(String key, String value) {
        getValues().put(key, value);
    }

}
