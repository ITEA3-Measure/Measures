package org.measure.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.measure.impl.data.RTCollection;
import org.measure.impl.data.RTDescription;
import org.measure.impl.data.RTElement;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class DirectMeasureImpl extends DirectMeasure {
    private Integer projectId;

    private Date fromDate;

    private Date toDate;

    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // Read Scope Properties
        String openProjectURL = getProperty("openprojectUrl");
        String apiKey = getProperty("apikey"); 
        String projectName = getProperty("projectName");
        Long lastExecution = new Long(0);
        try {
            lastExecution = Long.valueOf(getProperty("lastExecution"));
        } catch (Exception e) {
        } 
        
        this.toDate = new Date();
        
        if (lastExecution != 0) {
            this.fromDate = new Date(lastExecution);
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(this.toDate);
            c.add(Calendar.YEAR, -1);
            this.fromDate = c.getTime();
        
        }
        
        RestTemplate restTemplate = new RestTemplate();
        
        // add message converter for nullable String (to solve raw=null problem in RTDescription)
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.getObjectMapper().enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        
        restTemplate.getMessageConverters().add(0, messageConverter);
        
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor("apikey", apiKey));
        
        if(! openProjectURL.endsWith("/"))
            openProjectURL = openProjectURL + "/";
        RTCollection rTProjects = restTemplate.getForObject(openProjectURL + "api/v3/projects", RTCollection.class);
        
        // get project ID
        for(RTElement rTElement : rTProjects.get_embedded().getElements()) {
            if(rTElement.getName().equals(projectName)) {
                this.projectId = Integer.parseInt(rTElement.getId());
                break;
            }
        }
        if(this.projectId == null) {
            return result;
        }
        
        
        // format dates
        TimeZone tzUTC = TimeZone.getTimeZone("UTC");
        DateFormat dfZULU = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        dfZULU.setTimeZone(tzUTC);
        
        // construct url
        String url = openProjectURL 
                + "api/v3/projects/" 
                + projectId 
                + "/work_packages/?filters=[{\"updatedAt\":{\"operator\":\"<>d\",\"values\":[\""
                + dfZULU.format(this.fromDate)
                + "\",\""
                + dfZULU.format(this.toDate)
                + "\"]}}]&pageSize=100";
        
        
        
        RTCollection rTWorkPackages= restTemplate.getForObject(UriComponentsBuilder.fromUriString(url).build().toUri(), RTCollection.class);
        for(RTElement rTElement : rTWorkPackages.get_embedded().getElements()) {
        
        
            RTElement rtWorkPackage = restTemplate.getForObject(openProjectURL + "api/v3/work_packages/"+ rTElement.getId(), RTElement.class);
        
            DirectMeasureData data = new DirectMeasureData(this.toDate);
            data.addValue("issueId", rtWorkPackage.getId());
            data.addValue("summary", rtWorkPackage.getSubject());
            data.addValue("description", ((RTDescription)rtWorkPackage.getDescription()).getRaw());
            data.addValue("issuetype", rtWorkPackage.get_embedded().getType().getName());
            data.addValue("openProjectUrl", openProjectURL.substring(0, openProjectURL.indexOf("/openproject")) + rtWorkPackage.get_links().getSelf().getHref());
            data.addValue("projectId", getProjectId(rtWorkPackage.get_links().getProject().getHref()));
            data.addValue("projectName", rtWorkPackage.get_links().getProject().getTitle());
            data.addValue("version", rtWorkPackage.get_links().getVersion().getTitle());
            data.addValue("parentId", getWorkPackageId(rtWorkPackage.get_links().getParent().getHref()));
            data.addValue("parentSummary", rtWorkPackage.get_links().getParent().getTitle());
            data.addValue("creator", rtWorkPackage.get_links().getAuthor().getTitle());
            data.addValue("responsible", rtWorkPackage.get_links().getResponsible().getTitle());
            data.addValue("assignee", rtWorkPackage.get_links().getAssignee().getTitle());
            data.addValue("created", rtWorkPackage.getCreatedAt());
            data.addValue("updated", rtWorkPackage.getUpdatedAt());
            data.addValue("startdate", rtWorkPackage.getStartDate());
            data.addValue("duedate", rtWorkPackage.getDueDate());
            data.addValue("timeestimate", durationToHours(rtWorkPackage.getEstimatedTime()));
            data.addValue("timespent", durationToHours(rtWorkPackage.getSpentTime()));
            data.addValue("remainingTime", durationToHours(rtWorkPackage.getRemainingTime()));
            data.addValue("percentageDone", rtWorkPackage.getPercentageDone());
            data.addValue("priority", rtWorkPackage.get_links().getPriority().getTitle());
            data.addValue("status", rtWorkPackage.get_links().getStatus().getTitle());
            data.addValue("ontime", isOnTime(rtWorkPackage, this.toDate));       
            result.add(data);
        }
        
        
        
        
        // update lastExecution
        lastExecution = new Long(this.toDate.getTime());
        getProperties().put("lastExecution", lastExecution.toString());
        
        // Return data
        return result;
    }

    private static Integer getWorkPackageId(String workPackageHref) {
        if (workPackageHref != null) {
            String idStr = workPackageHref.replace("/openproject/api/v3/work_packages/", "");
            return Integer.valueOf(idStr);
        }
        return null;
    }

    private static Integer getProjectId(String projectHref) {
        String idStr = projectHref.replace("/openproject/api/v3/projects/", "");
        return Integer.valueOf(idStr);
    }

    private static Integer durationToHours(String stringDuration) throws DatatypeConfigurationException {
        if(stringDuration == null)
            return null;
        Duration duration = DatatypeFactory.newInstance().newDuration(stringDuration);
        int totalDurationInHours = duration.getYears();
        totalDurationInHours = totalDurationInHours * 12 + duration.getMonths();
        totalDurationInHours = totalDurationInHours * 30 + duration.getDays();
        totalDurationInHours = totalDurationInHours * 24 + duration.getHours();
        return totalDurationInHours;
    }

    private static String isOnTime(RTElement rtWorkPackage, Date toDate) {
        if(rtWorkPackage.getDueDate() != null  
                && rtWorkPackage.getDueDate().before(toDate) 
                && Integer.parseInt(rtWorkPackage.getPercentageDone()) != 100) {
            return "F";
        }
        return "T";
    }

}
