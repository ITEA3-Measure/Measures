package org.measure.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class DirectMeasureImpl extends DirectMeasure {

    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        String url = getProperty("url");
        String login = getProperty("login");
        String password = getProperty("password");
        String projectName = getProperty("projectName");
        String parameters = getProperty("parameters");
        
        
        Date from = Date.from(Instant.parse(getProperty("from"))) ; //parser.parse();
        
        Class.forName("com.mysql.jdbc.Driver");
        
        try (Connection connect = DriverManager.getConnection(url + "?" + "user=" + login + "&password=" + password + parameters)) {
            try (Statement statement = connect.createStatement()) {
        
                // 10:new
                ResultSet newSet = statement.executeQuery("select * from mantis_bug_table b INNER JOIN mantis_project_table p ON b.project_id=p.id  where p.name = '" + projectName + "' and date_submitted > " + from.getTime() / 1000);
        
                while (newSet.next()) {
                    DirectMeasureData data = new DirectMeasureData();
        
                    data.setName(newSet.getString("summary"));
                    data.setProject(newSet.getString("name"));
                    data.setOs(newSet.getString("os"));
                    data.setPlatform(newSet.getString("platform"));
                    data.setVersion(newSet.getString("version"));
                    data.setPriority(getPriority(newSet.getInt("priority")));
                    data.setReproductibility(getReproductibility(newSet.getInt("reproducibility")));
                    data.setSeverity(getSeverity(newSet.getInt("severity")));
                    data.setStatus(getStatus(newSet.getInt("status")));
                    data.setSubmited(new Date(newSet.getInt("date_submitted") * 1000L));
                    data.setLastupdate(new Date(newSet.getInt("last_updated") * 1000L));
                    result.add(data);
                }
            }
        }
              
        getProperties().put("from",  Instant.now().toString());
        return result;
    }

    private String getStatus(int value) {
        switch (value) {
        case 10:
            return "new";
        case 15:
            return "reviewed";
        case 20:
            return "feedback";
        case 25:
            return "in work";
        case 30:
            return "acknowledged";
        case 40:
            return "confirmed";
        case 50:
            return "assigned";
        case 55:
            return "usability";
        case 60:
            return "verify fail";
        case 70:
            return "verify wait";
        case 80:
            return "resolved";
        case 90:
            return "closed";
        }
        return "new";
    }

    private String getSeverity(int value) {
        switch (value) {
        case 10:
            return "feature";
        case 20:
            return "trivial";
        case 30:
            return "text";
        case 40:
            return "tweak";
        case 50:
            return "minor";
        case 55:
            return "usability";
        case 60:
            return "major";
        case 70:
            return "crash";
        case 80:
            return "block";
        
        }
        return "feature";
    }

    private String getReproductibility(int value) {
        switch (value) {
        case 10:
            return "always";
        case 30:
            return "sometimes";
        case 50:
            return "random";
        case 70:
            return "have not tried";
        case 90:
            return "unable";
        }
        return "N/A";
    }

    private String getPriority(int value) {
        switch (value) {
        case 10:
            return "low";
        case 20:
            return "normal";
        case 30:
            return "high";
        case 40:
            return "urgent";
        }
        return "imediat";
    }

}
