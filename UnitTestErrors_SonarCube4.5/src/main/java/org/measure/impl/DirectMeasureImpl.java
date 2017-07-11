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

@objid ("0dd11027-b3f1-4401-aaf8-301d76ada615")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("2eabcf97-8361-47a5-8864-9dc447df6d80")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        String serverURL = getProperty("ServerURL");
        String login = getProperty("Login");
        String password = getProperty("Password");
        String projectKey = getProperty("ProjectKey");
        
        try {
        
            Sonar sonar = Sonar.create(serverURL, login, password);
            Resource struts = sonar.find(ResourceQuery.createForMetrics(projectKey, "test_errors"));
            Measure m = struts.getMeasure("test_errors");
        
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
