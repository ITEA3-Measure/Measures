package org.measure.sonarqube.criticalviolations;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.measure.sonarqube.criticalviolations.data.RTHistory;
import org.measure.sonarqube.criticalviolations.data.RTMetric;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class DirectMeasureImpl extends DirectMeasure {

    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // TODO : Read Scope Properties
        String sonarQubeURL = getProperty("SonarQubeURL");
        String component = getProperty("Component");
        String from = getProperty("From");
        
        // TODO : Get Measurement
        SimpleDateFormat SONAR_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        
        Date fromDate = null;
        
        if (from != null && !"".equals(from)) {
            fromDate = Date.from(Instant.parse(from));
        } else {
            fromDate = new Date(0);
        }
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }
        
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // TODO Auto-generated method stub
            }
        });
        
        RTMetric res = restTemplate.getForObject(sonarQubeURL + "/api/measures/search_history?metrics=critical_violations&component=" + component + "&fromDateTime=" + SONAR_DATE_FORMAT.format(fromDate), RTMetric.class);
        if (res.getErrors() != null && res.getErrors().size() > 0) {
            throw new Exception(res.getErrors().get(0).getMsg());
        } else {
            if (res.getMeasures() != null && res.getMeasures().size() > 0)
                for (RTHistory metrics : res.getMeasures().get(0).getHistory()) {
                	if (metrics.getValue() == null) {
                		metrics.setValue("0");
                	}
                    DirectMeasureData data = new DirectMeasureData(SONAR_DATE_FORMAT.parse(metrics.getDate()), metrics.getValue());
                    result.add(data);
                }
        
            getProperties().put("From", Instant.now().toString());
        }
        
        // TODO : Set Return data
        return result;
    }

}
