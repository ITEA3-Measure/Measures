package org.measure.impl;

import java.util.Date;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DirectMeasureData extends DefaultMeasurement {

    public DirectMeasureData(Date date) {
        getValues().put("postDate", date);
    }

    public void addValue(String metricKey, String value) {
        getValues().put(metricKey, value);
    }

}
