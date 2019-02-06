package org.measure.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.restmodel.RTMeasure;
import org.measure.restmodel.RTResponse;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@objid ("1db9ff66-3851-45b5-811e-0ca24aae0101")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("adc71364-e20c-440b-90ed-c3c71677aa16")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // Read Scope Properties
        String sonarQubeURL = getProperty("ServerURL");
        String login = getProperty("Login");
        String password = getProperty("Password");
        String component = getProperty("ProjectKey");
        String metricKey = getProperty("Metric");
        
        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();
                
        // add message converter for null String
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.getObjectMapper().enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        restTemplate.getMessageConverters().add(0, messageConverter);
        
        // add basic auth (login, password)
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(login, password));
        
        
        // Measurement
        if(! sonarQubeURL.endsWith("/"))      
            sonarQubeURL = sonarQubeURL + "/";
        
        RTResponse response = restTemplate.getForObject(sonarQubeURL + "/api/measures/component?metricKeys=" + metricKey + "&componentKey=" + component, RTResponse.class);
        
        
        DirectMeasureData data = new DirectMeasureData(new Date());
        for(RTMeasure rTmeasure : response.getComponent().getMeasures()) {
            if(rTmeasure.getMetric().equals(metricKey)) {
                String value;
                if(rTmeasure.getValue() != null) {
                    value = rTmeasure.getValue();
                } else {
                    // no values, only periods, mostly with metrics beginning with "new". Example: new_violations.
                    value = rTmeasure.getPeriods().get(1).getValue();
                }
                data.addValue(value);
            }
        }
        
        // Return data
        result.add(data);
        return result;
    }

}
