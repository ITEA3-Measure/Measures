package org.measure.impl;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("c3b1b56b-163c-49b5-a38e-3ca86d01b919")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("2110dec0-5c3d-4489-ac54-ba6ba32a2a16")
    public DirectMeasureData(Date date) {
        getValues().put("postDate", date);
    }

    @objid ("2b80b534-d9e6-415b-8734-c05b23b6986f")
    public void addValue(String value) {
        getValues().put("value", value);
    }

}
