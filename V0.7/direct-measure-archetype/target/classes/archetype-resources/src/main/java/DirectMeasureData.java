#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.Date;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DirectMeasureData extends DefaultMeasurement {

    public DirectMeasureData() {

    }
    
    public DirectMeasureData(Date date) {
        getValues().put("postDate", date);
    }

    public void addValue(String key, String value) {
        getValues().put(key, value);
    }
}
