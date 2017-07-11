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

@objid ("f4bd8f98-2d19-493f-96fc-2dddab0e0986")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("b7a1b45b-abfa-4b56-bae7-d4e3510d62ab")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        String serverURL = getProperty("ServerURL");
        String login = getProperty("Login");
        String password = getProperty("Password");
        String projectKey = getProperty("ProjectKey");
        
        try {
        
            Sonar sonar = Sonar.create(serverURL, login, password);
            Resource struts = sonar.find(ResourceQuery.createForMetrics(projectKey, "uncovered_lines"));
            Measure m = struts.getMeasure("uncovered_lines");
        
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
