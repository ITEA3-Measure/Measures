package org.measure.impl;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("5a0a0fc2-0c91-4dcd-a06b-7056e4dd610d")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("b70337ab-9cbe-425f-8948-08e270c7e667")
    public DirectMeasureData(Date date) {
        getValues().put("postDate", date);
    }

    @objid ("252fad35-e5b0-4ece-9f9d-b40d98798beb")
    public void addValue(String metricKey, String value) {
        getValues().put(metricKey, value);
    }

}
