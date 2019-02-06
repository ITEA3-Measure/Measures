package org.measure.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.measure.impl.data.RTBuild;
import org.measure.impl.data.RTJob;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

public class DirectMeasureImpl extends DirectMeasure {

    private Integer fromBuild;

    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // Read Scope Properties
        String jenkinsURL = getProperty("url");
        String user = getProperty("user"); 
        String password = getProperty("password"); 
        String jobName = getProperty("jobName");
        Integer lastBuild = new Integer(0);
        try {
            lastBuild = Integer.valueOf(getProperty("lastBuild"));
        } catch (Exception e) {
        } 
        this.fromBuild = lastBuild;
        
        
        
        // Get Measurement
        
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor(user, password));
        if(! jenkinsURL.endsWith("/"))
                jenkinsURL = jenkinsURL + "/";
        RTJob rTJob = restTemplate.getForObject(jenkinsURL + "job/" + jobName + "/api/json", RTJob.class);
        rTJob.get_class();
        
        Date postDate = new Date();
        
        for(RTBuild build: rTJob.getBuilds()) {
            if(Integer.valueOf(build.getNumber()) > this.fromBuild) {
                
                DirectMeasureData data = new DirectMeasureData(postDate);
        
                // add job information
                data.addValue("jobClazz", rTJob.get_class());
                data.addValue("jobName", rTJob.getName());
                data.addValue("jobUrl", rTJob.getUrl());
                
                
                // add build information
                RTBuild rTBuild = restTemplate.getForObject(jenkinsURL + "job/" + jobName + "/" + build.getNumber() + "/api/json", RTBuild.class);
                data.addValue("buildClazz", rTBuild.get_class());
                data.addValue("buildNumber", rTBuild.getNumber());
                data.addValue("buildUrl", rTBuild.getUrl());
                data.addValue("buildDescription", rTBuild.getDescription());
                data.addValue("duration", rTBuild.getDuration());
                data.addValue("estimatedDuration", rTBuild.getEstimatedDuration());
                data.addValue("result", rTBuild.getResult());
                data.addValue("timestamp", rTBuild.getTimestamp());
                
                result.add(data);
        
                if(Integer.valueOf(build.getNumber()) > lastBuild) {
                    lastBuild = Integer.valueOf(build.getNumber());
                }
            }
        }
        
        
        
        // update lastBuild
        getProperties().put("lastBuild", lastBuild.toString());
        
        
        // Set Return data
        return result;
    }

}
