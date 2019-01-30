package org.measure.github.githubissue;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GitHub;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class GitHubMeasureImpl extends DirectMeasure {
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<>();
        
        String login = getProperty("Login");
        String githubAuthKey = getProperty("AccessTocken");
        String domain = getProperty("Organisation");
        String repository = getProperty("Repository");
        String since = getProperty("CollectDataSince");
        
        GitHub gh = GitHub.connect(login, githubAuthKey);
        for(GHIssue issue :  gh.getRepository(domain + "/" + repository).getIssues(GHIssueState.ALL)) {
        	Date date = issue.getUpdatedAt();
        	if (date.compareTo(Date.from(Instant.parse(since))) > 0) {
        	     GitHubMeasurement measurement = new GitHubMeasurement();
        	     measurement.getValues().put("Id", issue.getId());
                 measurement.getValues().put("Title", issue.getTitle());
                 measurement.getValues().put("Body", issue.getBody());    
                 measurement.getValues().put("State", issue.getState().toString());
                 measurement.getValues().put("CreatedAt", issue.getCreatedAt());
                 measurement.getValues().put("UpdatedAt", issue.getUpdatedAt());
                 measurement.getValues().put("ClosedAt", 	issue.getClosedAt());
                 if(issue.getClosedBy() != null) {
                     measurement.getValues().put("ClosedBy", issue.getClosedBy().getLogin());
                 }
                 
                 if(issue.getAssignee() != null) {
                     measurement.getValues().put("Assignee", issue.getAssignee().getLogin());
                 }
     
                 measurement.getValues().put("CommentsCount",issue.getCommentsCount() );
                 measurement.getValues().put("Author", issue.getUser().getLogin());
       
                 result.add(measurement); 
        	}
        }
        
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'z'");
        getProperties().put("CollectDataSince", DATE_FORMAT.format(new Date()));

        
        return result;
    }

}
