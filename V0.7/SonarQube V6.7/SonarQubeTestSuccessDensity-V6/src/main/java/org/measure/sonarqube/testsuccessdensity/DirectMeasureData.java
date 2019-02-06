package org.measure.sonarqube.testsuccessdensity;

import java.util.Date;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DirectMeasureData extends DefaultMeasurement {

	public DirectMeasureData(Date date, String value) {
        getValues().put("postDate", date);
        getValues().put("value", value);
    }
    
    public DirectMeasureData(Date date) {
        getValues().put("postDate", date);
    }

    public void addValue(String key, String value) {
        getValues().put(key, value);
    }
}
