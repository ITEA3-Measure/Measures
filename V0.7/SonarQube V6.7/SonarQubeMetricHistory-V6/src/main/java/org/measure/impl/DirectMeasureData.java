package org.measure.impl;

import java.util.Date;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DirectMeasureData extends DefaultMeasurement {
    public DirectMeasureData(Date date, String value) {
        getValues().put("postDate", date);
        getValues().put("value", value);
    }

}
