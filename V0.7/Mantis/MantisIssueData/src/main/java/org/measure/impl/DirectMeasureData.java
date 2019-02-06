package org.measure.impl;

import java.util.Date;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DirectMeasureData extends DefaultMeasurement {

    public void setName(String value) {
        getValues().put("name", value);
    }

    public void setProject(String value) {
        getValues().put("project", value);
    }

    public void setPriority(String value) {
        getValues().put("priority", value);
    }

    public void setSeverity(String value) {
        getValues().put("severity", value);
    }

    public void setReproductibility(String value) {
        getValues().put("reproductibility", value);
    }

    public void setStatus(String value) {
        getValues().put("status", value);
    }

    public void setOs(String value) {
        getValues().put("os", value);
    }

    public void setPlatform(String value) {
        getValues().put("platform", value);
    }

    public void setVersion(String value) {
        getValues().put("version", value);
    }

    public void setSubmited(Date value) {
        getValues().put("submited", value);
    }

    public void setLastupdate(Date value) {
        getValues().put("lastupdate", value);
    }

}
