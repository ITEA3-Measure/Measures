package org.measure.github.githubrepository;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.GHRepository;
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
        
        GitHub gh = GitHub.connect(login, githubAuthKey);
        GHRepository grepository = gh.getRepository(domain + "/" + repository);
        
        GitHubMeasurement measurement = new GitHubMeasurement();
        measurement.getValues().put("name", grepository.getName());
        measurement.getValues().put("created at", grepository.getCreatedAt());
        measurement.getValues().put("updated at", grepository.getUpdatedAt());
        measurement.getValues().put("description", grepository.getDescription());     
        measurement.getValues().put("language", grepository.getLanguage());
        measurement.getValues().put("owner", grepository.getOwnerName());
        measurement.getValues().put("star", grepository.getStargazersCount());
        measurement.getValues().put("suscriber", grepository.getSubscribersCount());
        measurement.getValues().put("fork", grepository.getForks());
        measurement.getValues().put("branche", grepository.getBranches().size());
        result.add(measurement);
        
        return result;
    }

}
