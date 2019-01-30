package org.measure.github.hithubissue;

import static java.time.temporal.ChronoUnit.DAYS;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
    	
    	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'z'");
    	
    	
        List<IMeasurement> result = new ArrayList<>();
        
        String login = getProperty("Login");
        String githubAuthKey = getProperty("AccessTocken");
        String domain = getProperty("Organisation");
        String repository = getProperty("Repository");
        String since = getProperty("CollectDataSince");
        
        
        
        GitHub gh = GitHub.connect(login, githubAuthKey);
        List<GHIssue> issues = gh.getRepository(domain + "/" + repository).getIssues(GHIssueState.ALL);
        
        
        
        Date firstIssue =  issues.get(issues.size() - 1).getCreatedAt();
        Date from  = Date.from(Instant.parse(since));
        if(firstIssue.getTime() > from.getTime()) {
        	from = firstIssue;
        }
        
		Date to = Date.from(ZonedDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).plusWeeks(1).toInstant());
		
		ZonedDateTime current = ZonedDateTime.ofInstant(from.toInstant(), ZoneId.systemDefault()).truncatedTo(DAYS);
		
		while(current.toInstant().toEpochMilli() <= to.getTime()) {
			
			int openIssues = 0;
			int closedIssues = 0;
			int totalIssues = 0;
			long solveDurationTotal = 0;
			
			
			for(GHIssue issue : issues ) {			
				if(issue.getCreatedAt().getTime() < current.toInstant().toEpochMilli()) {
					if(issue.getClosedAt() != null && issue.getClosedAt().getTime()  < current.toInstant().toEpochMilli()) {
						closedIssues ++;		
						solveDurationTotal = solveDurationTotal + ((issue.getClosedAt().getTime() - issue.getCreatedAt().getTime())/86400000);
					}else {
						openIssues ++;
					}
					totalIssues++;
				}			
			}	
		
			
   	     	GitHubMeasurement measurement = new GitHubMeasurement();
   	        measurement.getValues().put("postDate", DATE_FORMAT.format(new Date(current.toInstant().toEpochMilli())));
   	     	measurement.getValues().put("Open", openIssues);
            measurement.getValues().put("Close", closedIssues);
            measurement.getValues().put("Total", totalIssues);    

           if(closedIssues > 0) {
               	Long duratioon = solveDurationTotal / new Long(closedIssues);
                measurement.getValues().put("OpenDuration", duratioon);
            }else {
            	  measurement.getValues().put("OpenDuration", 0);
            }
            result.add(measurement);
            
            if(to.getTime() - current.toInstant().toEpochMilli() < 5184000000L) {
            	current = current.plusDays(1);
            }else {
            	current = current.plusWeeks(1);
            }
		
		}
	
        
        getProperties().put("CollectDataSince", DATE_FORMAT.format(new Date()));

        
        return result;
    }

}
