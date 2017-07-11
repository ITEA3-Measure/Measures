package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

@objid ("3495a3f4-25ce-446c-ab44-e0c36ef6d87c")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("0892c39a-fe39-4bf8-9cbd-00100993d434")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        String serverURL = getProperty("ServerURL");
        String login = getProperty("Login");
        String password = getProperty("Password");
        String projectKey = getProperty("ProjectKey");
        String severity = getProperty("Severity");
        
        if(severity == null || "".equals(severity)){
            severity = "blocker";
        }
        try {
        
            Sonar sonar = Sonar.create(serverURL, login, password);
            Resource struts = sonar.find(ResourceQuery.createForMetrics(projectKey, severity+"_violations"));
            Measure m = struts.getMeasure(severity+"_violations");
        
            List<IMeasurement> result = new ArrayList<IMeasurement>();
            DoubleMeasurement data = new DoubleMeasurement();
            data.setValue(m.getValue());
            result.add(data);
        
            return result;
        } catch (Exception e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
    }

}
