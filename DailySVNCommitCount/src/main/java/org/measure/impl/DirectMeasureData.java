package org.measure.impl;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("9b3fb425-0e55-46aa-9db2-153c97457194")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("b2f63d08-a5df-4692-a9e2-89bc8fbaf64a")
    public DirectMeasureData(Integer value, Date day) {
        getValues().put("commits", value);
        getValues().put("postDate", day);
    }

}
