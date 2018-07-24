package org.measure.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.springframework.web.client.RestTemplate;

@objid ("a94be628-5aae-42f7-9931-01e27d28c46f")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("a87e4ee7-9474-4ebc-8d75-a856fa21415e")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
               //Read Scope Properties
               String sonarQubeURL = getProperty("SonarQubeURL");
               String metric = getProperty("Metric");
               String component = getProperty("Component");
        
        
        
               RestTemplate restTemplate = new RestTemplate();
        try {
            RTMetric res = restTemplate.getForObject(sonarQubeURL + "/api/measures/component?metricKeys=" +metric + "&component=" + component, RTMetric.class);
            DirectMeasureData data = new DirectMeasureData(new Date(),res.getValue());
            result.add(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
