package org.measure.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.data.RTMetric;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@objid ("a94be628-5aae-42f7-9931-01e27d28c46f")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("a87e4ee7-9474-4ebc-8d75-a856fa21415e")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        // Read Scope Properties
        String sonarQubeURL = getProperty("SonarQubeURL");
        String metric = getProperty("Metric");
        String component = getProperty("Component");
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
        
        RTMetric res = restTemplate.getForObject(sonarQubeURL + "/api/measures/component?metricKeys=" + metric + "&component=" + component, RTMetric.class);
        
        if (res.getErrors() != null && res.getErrors().size() > 0) {
            throw new Exception(res.getErrors().get(0).getMsg());
        } else {
            DirectMeasureData data = new DirectMeasureData(new Date(), res.getValue());
            result.add(data);
        }
        return result;
    }

}
