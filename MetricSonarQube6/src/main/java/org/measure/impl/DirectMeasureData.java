package org.measure.impl;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("316c30c5-247c-4c88-9a5f-c71bd0e058e9")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("5cfa1839-f70e-475f-adda-a9a269b4a5e9")
    public DirectMeasureData(Date date, String value) {
        getValues().put("postDate", date);
        getValues().put("value", value);
    }

}
