package org.measure.github.githuborganisation;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.GHOrganization;
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
        
        GitHub gh = GitHub.connect(login, githubAuthKey);
        GHOrganization organisation = gh.getOrganization(domain);


        IMeasurement measurement = new GitHubMeasurement();
        
        measurement.getValues().put("name",organisation.getName());
        measurement.getValues().put("creationdate",organisation.getCreatedAt());
        measurement.getValues().put("updatedate",organisation.getUpdatedAt());
        measurement.getValues().put("compagny",organisation.getCompany());
        
        measurement.getValues().put("follower", organisation.getFollowersCount());
        measurement.getValues().put("following", organisation.getFollowingCount());
        measurement.getValues().put("memeber", organisation.getMembers().size());
        measurement.getValues().put("public_repo",organisation.getPublicRepoCount());
        measurement.getValues().put("public_gist", organisation.getPublicGistCount());
        
        result.add(measurement);
        return result;
    }

}
