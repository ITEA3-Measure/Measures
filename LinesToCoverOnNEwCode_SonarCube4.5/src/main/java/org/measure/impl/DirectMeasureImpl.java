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

@objid ("e1560db4-55dc-4cf7-8fcb-11d0290660aa")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("23abb2ea-2288-4eac-ac18-7e6ae8fcf3e1")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        String serverURL = getProperty("ServerURL");
        String login = getProperty("Login");
        String password = getProperty("Password");
        String projectKey = getProperty("ProjectKey");
        
        try {
        
            Sonar sonar = Sonar.create(serverURL, login, password);
            Resource struts = sonar.find(ResourceQuery.createForMetrics(projectKey, "new_lines_to_cover"));
            Measure m = struts.getMeasure("new_lines_to_cover");
        
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
