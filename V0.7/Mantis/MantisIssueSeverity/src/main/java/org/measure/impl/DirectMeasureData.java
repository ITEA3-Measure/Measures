package org.measure.impl;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DirectMeasureData extends DefaultMeasurement {

    public void setFeatures(Integer value) {
        getValues().put("features", value);
    }

    public void setMinors(Integer value) {
        getValues().put("minors", value);
    }

    public void setMajors(Integer value) {
        getValues().put("majors", value);
    }

    public void setCrashs(Integer value) {
        getValues().put("crashs", value);
    }

    public void setBlocks(Integer value) {
        getValues().put("blocks", value);
    }

}
