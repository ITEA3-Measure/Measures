package org.measure.impl;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("4f615287-f611-491b-a259-8fcbb35afebb")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("ddca293f-1c3a-4e96-9239-de69628a6068")
    public DirectMeasureData(Integer value, Date day) {
        getValues().put("commits", value);
        getValues().put("postDate", day);
    }

}
