package org.measure.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
        
        Class.forName("com.mysql.jdbc.Driver");
        
        try (Connection connect = DriverManager.getConnection(url + "?" + "user=" + login + "&password=" + password + parameters)) {
            try (Statement statement = connect.createStatement()) {
        
                // feature
                ResultSet featureSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id  where b.status < 80 and b.severity < 50 and p.name = '" + projectName + "'");
                featureSet.next();
                int featureCount = featureSet.getInt(1);
        
                // minor
                ResultSet minorSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id  where b.status = 50 and b.severity < 50 and p.name = '" + projectName + "'");
                minorSet.next();
                int minorCount = minorSet.getInt(1);
        
                // major
                ResultSet majorSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id  where b.status = 60 and b.severity < 50 and p.name = '" + projectName + "'");
                majorSet.next();
                int majorCount = majorSet.getInt(1);
        
                // crash
                ResultSet crashSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id  where b.status = 70 and b.severity < 50 and p.name = '" + projectName + "'");
                crashSet.next();
                int crashCount = crashSet.getInt(1);
        
                // block
                ResultSet blockSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id  where b.status = 80 and b.severity < 50 and p.name = '" + projectName + "'");
                blockSet.next();
                int blockCount = blockSet.getInt(1);
        
                DirectMeasureData data = new DirectMeasureData();
                data.setBlocks(blockCount);
                data.setCrashs(crashCount);
                data.setMajors(majorCount);
                data.setMinors(minorCount);
                data.setFeatures(featureCount);
                result.add(data);
            }
        }
        return result;
    }

}
