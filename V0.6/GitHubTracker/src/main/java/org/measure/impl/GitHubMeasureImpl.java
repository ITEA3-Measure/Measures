package org.measure.impl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterator;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("a493f18b-616d-444a-8dce-20a0a1769e7a")
public class GitHubMeasureImpl extends DirectMeasure {
    @objid ("1b63dae7-162c-4619-83d8-20510c05a3b9")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<>();
        
        String login = getProperty("Login");
        String githubAuthKey = getProperty("GitHubAuth");
        String domain = getProperty("Domain");
        String repository = getProperty("Repository");
        String since = getProperty("Since");
        
        GitHub gh = GitHub.connect(login, githubAuthKey);
        for (PagedIterator localPagedIterator = gh.getRepository(domain + "/" + repository).listCommits().iterator(); localPagedIterator.hasNext();) {
            GHCommit c = (GHCommit) localPagedIterator.next();
            Date date = c.getCommitDate();
            if (date.compareTo(Date.from(Instant.parse(since))) > 0) {
                result.add(new GitHubMeasurement(c.getAuthor().getName(), c.getCommitDate(),
                        c.getCommitShortInfo().getMessage(), Integer.valueOf(c.getLinesAdded()),
                        Integer.valueOf(c.getLinesChanged()), Integer.valueOf(c.getLinesDeleted())));
            }
        
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'z'");
            getProperties().put("Since", DATE_FORMAT.format(new Date()));
        }
        return result;
    }

}
