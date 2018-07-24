package org.measure.impl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.springframework.web.client.RestTemplate;

@objid ("62c78a91-63dd-4e77-a55e-9d0ddc1c8825")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("45bd1a88-53e8-4323-aff4-e3864de1bd34")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // Read Scope Properties
        String sonarQubeURL = getProperty("SonarQubeURL");
        String metric = getProperty("Metric");
        String component = getProperty("Component");
        String from = getProperty("From");
        
        SimpleDateFormat SONAR_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        
        Date fromDate = null;
        
        if(from != null && !"".equals(from)){
            fromDate = Date.from(Instant.parse(from));
        }else{
            fromDate = new Date(0);
        }
        
        RestTemplate restTemplate = new RestTemplate();
        try {
            System.out.println(sonarQubeURL + "/api/measures/search_history?metrics=" + metric + "&ps=1000&component=" + component + "&fromDateTime=" + SONAR_DATE_FORMAT.format(fromDate));
            RTMetric res = restTemplate.getForObject(sonarQubeURL + "/api/measures/search_history?metrics=" + metric + "&component=" + component + "&fromDateTime=" + SONAR_DATE_FORMAT.format(fromDate), RTMetric.class);
        
            if(res.getMeasures() != null && res.getMeasures().size() > 0)
            for (RTHistory metrics : res.getMeasures().get(0).getHistory()) {
                DirectMeasureData data = new DirectMeasureData(SONAR_DATE_FORMAT.parse(metrics.getDate()), metrics.getValue());
                result.add(data);
            }
            
            getProperties().put("From",  Instant.now().toString());
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
