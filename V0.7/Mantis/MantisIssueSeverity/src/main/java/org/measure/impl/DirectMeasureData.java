package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("2e9a7f4f-3359-404c-b3bf-cdbdec0dbe80")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("52bfc719-5620-415d-80aa-72cac45be4df")
    public void setFeatures(Integer value) {
        getValues().put("features", value);
    }

    @objid ("add8ad90-dd58-4ece-bc63-bd38dd0cb504")
    public void setMinors(Integer value) {
        getValues().put("minors", value);
    }

    @objid ("47fef7a6-c736-43c0-9a58-721c442ec6e7")
    public void setMajors(Integer value) {
        getValues().put("majors", value);
    }

    @objid ("d7b9f715-7675-4552-9c44-85ce45ff8b94")
    public void setCrashs(Integer value) {
        getValues().put("crashs", value);
    }

    @objid ("3b112b7a-242a-462a-b47f-88d01f653bcf")
    public void setBlocks(Integer value) {
        getValues().put("blocks", value);
    }

}
