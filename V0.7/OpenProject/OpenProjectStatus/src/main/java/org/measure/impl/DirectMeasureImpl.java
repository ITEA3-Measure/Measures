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
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.resttemplatemodel.RTCollection;
import org.measure.resttemplatemodel.RTElement;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@objid ("3c9a825c-58cc-4168-ae36-b2198051b1cf")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("4ae95615-1d13-437e-9264-2db6018ce119")
    private Integer projectId;

    @objid ("d3d372b8-f4a6-4473-9a6c-a3149985b2f9")
    private Date toDate;

    @objid ("38638a90-1504-43a9-8915-6ed76a92ea1d")
    private Date fromDate;

    @objid ("ccfeff98-a729-422e-8e88-218f6f02b51d")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // Read Scope Properties
        String openProjectURL = getProperty("openprojectUrl");
        String apiKey = getProperty("apikey"); 
        String projectName = getProperty("projectName");
        
        this.toDate = new Date();
        
        Calendar c = Calendar.getInstance();
        c.setTime(this.toDate);
        c.add(Calendar.YEAR, -8);
        this.fromDate = c.getTime();
        
        
        // Get Measurement
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
        
        
        // initialize local variables
        int totalEstimatedTime =0;
        int totalSpentTime = 0;
        int totalRemainingTime = 0;
        int openFeatures = 0;
        int totalFeatures = 0;
        int openTasks = 0;
        int totalTasks = 0;
        int openMilestones = 0;
        int totalMilestones = 0;
        int openPhases = 0;
        int totalPhases = 0;
        int openEpics = 0;
        int totalEpics = 0;
        int openUserStories = 0;
        int totalUserStories = 0;
        int openBugs = 0;
        int totalBugs = 0;
        int openSpecifications = 0;
        int totalSpecifications = 0;
        int openQualityRequirements = 0;
        int totalQualityRequirements = 0;
        int open = 0;
        int total = 0;
        
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
        
        
        // GET
        RTCollection rTWorkPackages= restTemplate.getForObject(UriComponentsBuilder.fromUriString(url).build().toUri(), RTCollection.class);
        for(RTElement rTElement : rTWorkPackages.get_embedded().getElements()) {
        
            if(rTElement.get_links().getParent().getTitle() == null) {
                totalEstimatedTime += durationToHours(rTElement.getEstimatedTime());
                totalSpentTime += durationToHours(rTElement.getSpentTime());
                totalRemainingTime += durationToHours(rTElement.getRemainingTime());
            }
        
            if(! rTElement.get_links().getStatus().getTitle().equals("Rejected")) {
                if(! rTElement.get_links().getStatus().getTitle().equals("Closed")) {
                    open++;
                    switch ( rTElement.get_links().getType().getTitle()) {
                        case "Feature":  openFeatures++;
                                 break;
                        case "Task":  openTasks++;
                                 break;
                        case "Milestone": openMilestones++;
                                 break;
                        case "Phase": openPhases++;
                                 break;
                        case "Epic:": openEpics++;
                                 break;
                        case "User story": openUserStories++;
                                 break;
                        case "Bug": openBugs++;
                                 break;
                        case "Specification": openSpecifications++;
                                 break;
                        case "QualityRequirement": openQualityRequirements++;
                                 break;
                    }
                    
                }
                total++;
                switch ( rTElement.get_links().getType().getTitle()) {
                    case "Feature":  totalFeatures++;
                             break;
                    case "Task":  totalTasks++;
                             break;
                    case "Milestone": totalMilestones++;
                             break;
                    case "Phase":  totalPhases++;
                             break;
                    case "Epic:":  totalEpics++;
                             break;
                    case "User story":  totalUserStories++;
                             break;
                    case "Bug": totalBugs++;
                             break;
                    case "Specification": totalSpecifications++;
                             break;
                    case "QualityRequirement": totalQualityRequirements++;
                             break;
                }
                
            }
        }
        
        // Set Return data
        DirectMeasureData data = new DirectMeasureData(this.toDate);
        
        data.addValue("totalEstimatedTime", totalEstimatedTime);       
        data.addValue("totalSpentTime", totalSpentTime);       
        data.addValue("totalRemainingTime", totalRemainingTime);  
        data.addValue("openFeatures", openFeatures);  
        data.addValue("totalFeatures", totalFeatures);  
        data.addValue("openTasks", openTasks);  
        data.addValue("totalTasks", totalTasks);  
        data.addValue("openMilestones", openMilestones);  
        data.addValue("totalMilestones", totalMilestones);  
        data.addValue("openPhases", openPhases);  
        data.addValue("totalPhases", totalPhases);  
        data.addValue("openEpics", openEpics);  
        data.addValue("totalEpics", totalEpics);  
        data.addValue("openUserStories", openUserStories);  
        data.addValue("totalUserStories", totalUserStories);  
        data.addValue("openBugs", openBugs);  
        data.addValue("totalBugs", totalBugs);  
        data.addValue("openSpecifications", openSpecifications);  
        data.addValue("totalSpecifications", totalSpecifications);  
        data.addValue("openQualityRequirements", openQualityRequirements);  
        data.addValue("totalQualityRequirements", totalQualityRequirements);  
        data.addValue("open", open);  
        data.addValue("total", total);  
        
        result.add(data);
        return result;
    }

    @objid ("651a9976-9ad4-432b-8747-baf30b74333a")
    private static Integer durationToHours(String stringDuration) throws DatatypeConfigurationException {
        if(stringDuration == null)
            return 0;
        Duration duration = DatatypeFactory.newInstance().newDuration(stringDuration);
        int totalDurationInHours = duration.getYears();
        totalDurationInHours = totalDurationInHours * 12 + duration.getMonths();
        totalDurationInHours = totalDurationInHours * 30 + duration.getDays();
        totalDurationInHours = totalDurationInHours * 24 + duration.getHours();
        return totalDurationInHours;
    }

}
