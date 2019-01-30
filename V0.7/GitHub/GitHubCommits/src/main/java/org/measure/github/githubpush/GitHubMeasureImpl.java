package org.measure.github.githubpush;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterator;
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
        for (PagedIterator localPagedIterator = gh.getRepository(domain + "/" + repository).listCommits().iterator(); localPagedIterator.hasNext();) {
            GHCommit c = (GHCommit) localPagedIterator.next();
            Date date = c.getCommitDate();
            if (date.compareTo(Date.from(Instant.parse(since))) > 0) {
            	
            	IMeasurement measurement = new GitHubMeasurement();
            	
            	if(c.getAuthor() != null) {
            		measurement.getValues().put("author", c.getAuthor().getName());
            	}else {
            		measurement.getValues().put("author", null);
            	}
            
            	measurement.getValues().put("postDate", c.getCommitDate());
            	if(c.getCommitShortInfo() != null) {
            		measurement.getValues().put("commitMessage", c.getCommitShortInfo().getMessage());
            	}else {
            		measurement.getValues().put("commitMessage", null);
            	}
            
            	measurement.getValues().put("LigneAdd", c.getLinesAdded());
            	measurement. getValues().put("LigneChanged", c.getLinesChanged());
            	measurement. getValues().put("LigneDelete", c.getLinesDeleted());
            	result.add(measurement);
            }
        
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'z'");
            getProperties().put("CollectDataSince", DATE_FORMAT.format(new Date()));
        }
        
        return result;
    }

}
