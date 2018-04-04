package org.measure.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("7e2c2345-229c-42e9-9996-eb882a3b0d28")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("8f9d3eb9-f593-4ca2-a85e-c81e41f2c58a")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        String url = getProperty("url");
        String login = getProperty("login");
        String password = getProperty("password");
        String projectName = getProperty("projectName");
        
        
        Date from = Date.from(Instant.parse(getProperty("from"))) ; //parser.parse();
        
        Class.forName("com.mysql.jdbc.Driver");
        
        try (Connection connect = DriverManager.getConnection(url + "?" + "user=" + login + "&password=" + password + "")) {
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

    @objid ("7fc8110a-98cf-4683-bb49-150666895717")
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

    @objid ("aca7369b-67f0-424b-a6e9-acb68b1264b7")
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

    @objid ("4bf39936-5696-44e4-9b22-57d5b674dd06")
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

    @objid ("94fd16e8-7ef8-42c3-86ad-95e72774b50d")
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
