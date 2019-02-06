package org.measure.impl;

import java.util.Date;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("8fcab826-b805-48c8-a9d9-2edba07a2ea9")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("31cfd6d6-630d-4af1-83a1-cd814d6fa98b")
    public DirectMeasureData(Date date) {
        getValues().put("postDate", date);
    }

    @objid ("6f95fcb3-6162-4422-9509-f46f91424680")
    public void addValue(String key, String value) {
        getValues().put(key, value);
    }

}
