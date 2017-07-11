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

@objid ("4d9fe867-ffdf-42a8-9e0d-c1824cab247f")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("fb66d91d-ff05-4f61-bfce-6e60f58dc5be")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        String serverURL = getProperty("ServerURL");
        String login = getProperty("Login");
        String password = getProperty("Password");
        String projectKey = getProperty("ProjectKey");
        
        try {
        
            Sonar sonar = Sonar.create(serverURL, login, password);
            Resource struts = sonar.find(ResourceQuery.createForMetrics(projectKey, "public_undocumented_api"));
        
            List<IMeasurement> result = new ArrayList<IMeasurement>();
            DoubleMeasurement data = new DoubleMeasurement();
            Measure m = struts.getMeasure("public_undocumented_api");
            data.setValue(m.getValue());
            result.add(data);
        
            return result;
        } catch (Exception e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }            

    }

}
