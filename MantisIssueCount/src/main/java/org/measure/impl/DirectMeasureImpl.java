package org.measure.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("4adca81f-a7be-47e9-a972-d7d2409fc031")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("3f4a9539-1400-4a29-ac9d-814ecbcaacb9")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // TODO : Read Scope Properties
        
        String url = getProperty("url");
        String login = getProperty("login");
        String password = getProperty("password");
        String projectName = getProperty("projectName");
        String severity = getProperty("severity");
        
        int severityValue = 10;
        if("Minor".equals(severity)){
            severityValue = 50;
        }else if("Major".equals(severity)){
            severityValue = 60;
        }if("Crash".equals(severity)){
            severityValue = 70;
        }if("Block".equals(severity)){
            severityValue = 80;
        }
        
        
        Class.forName("com.mysql.jdbc.Driver");
        
        
        try(Connection connect = DriverManager.getConnection(url + "?"+ "user="+login+"&password="+password+"")) {
            try(Statement statement = connect.createStatement()){
                    
                // 10:new
                ResultSet newSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id  where b.status = 10 and b.severity >= "+ severityValue +" and p.name = '"+projectName+"'");                   
                newSet.next();
                int newCount = newSet.getInt(1);
                
                //  30:acknowledged
                ResultSet acknowledgedSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id where b.status = 30 and b.severity >= "+ severityValue +" and p.name = '"+projectName+"'");                   
                acknowledgedSet.next();
                int acknowledgedCount = acknowledgedSet.getInt(1);
                
                // 50:assigned
                ResultSet assignedSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id where b.status = 50 and b.severity >= "+ severityValue +" and p.name = '"+projectName+"'");                   
                assignedSet.next();
                int assignedCount = assignedSet.getInt(1);
                          
                // 90:closed
                ResultSet closedSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id where b.status = 90 and b.severity >= "+ severityValue +" and p.name = '"+projectName+"'");                   
                closedSet.next();
                int closedCount = closedSet.getInt(1);
                
                // 40:confirmed
                ResultSet confirmedSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id where b.status = 40 and b.severity >= "+ severityValue +" and p.name = '"+projectName+"'");                   
                confirmedSet.next();
                int confirmedCount = confirmedSet.getInt(1);
                                
                // 20:feedback
                ResultSet feedbackSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id where b.status = 20 and b.severity >= "+ severityValue +" and p.name = '"+projectName+"'");                   
                feedbackSet.next();
                int feedbackCount = feedbackSet.getInt(1);
                
                // 80:resolved
                ResultSet resolvedSet = statement.executeQuery("select COUNT(*) from mantis_bug_table b  INNER JOIN mantis_project_table p ON b.project_id=p.id where b.status = 80 and b.severity >= "+ severityValue +" and p.name = '"+projectName+"'");                   
                resolvedSet.next();
                int resolvedCount = resolvedSet.getInt(1);
                            
                DirectMeasureData data = new DirectMeasureData();
        
                data.setAcknowledgedIssueCount(acknowledgedCount);
                data.setAllIssuesCount(newCount + acknowledgedCount + assignedCount + closedCount + confirmedCount + feedbackCount + resolvedCount);
                data.setAssignedIssueCount(assignedCount); 
                data.setClosedIssueCount(closedCount);
                data.setConfirmedIssueCount(confirmedCount);
                data.setFeedbackIssueCount(feedbackCount);
                data.setNewIssueCount(newCount);
                data.setOpenIssuesCount(newCount + acknowledgedCount + assignedCount + confirmedCount + feedbackCount);
                data.setResolvedIssueCount(resolvedCount);
                
                result.add(data);
            }
        }
        
        
        
        // TODO : Set Return data
        return result;
    }

}
