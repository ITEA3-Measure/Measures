package org.measure.impl;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("3490f862-4c78-4275-8fe6-b7a9bd688891")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("d9ac47f4-4660-4d15-a6d8-d8e87fa0eb52")
    public DirectMeasureData(Date date, String value) {
        getValues().put("postDate", date);
        getValues().put("value", value);
    }

}
