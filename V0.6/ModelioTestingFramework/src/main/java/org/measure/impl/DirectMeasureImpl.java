package org.measure.impl;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@objid ("119bf5fb-7338-40c6-9f60-14593e3c5302")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("29e66bd9-f651-489a-9a41-bfad4afe295e")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        String testReportUrl = getProperty("testReportUrl");
        String lastSession = getProperty("lastSession");
        String project = getProperty("project");
        
        URL url = new URL(testReportUrl);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(url.openStream());
        NodeList reports = doc.getElementsByTagName("test-report");
        
        if (reports.getLength() > 0) {
            Node report = reports.item(0);
            if (report.getNodeType() == Node.ELEMENT_NODE) {
                String sessionId = ((Element) report).getAttribute("id");
        
                if (lastSession != null  && lastSession.equals(sessionId)) {
                    return result;
                } 
        
                Date executionDate = new Date();
                DateFormat parser = new SimpleDateFormat("'S'yyyy-MM-dd'_'HH'h'mm'm'ss's'"); 
        
                try {
                    executionDate = parser.parse(sessionId);
                } catch (Exception e) {
        
                }
        
                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); 
                formater.setTimeZone(TimeZone.getTimeZone("UTC"));
            
        
                NodeList tests = doc.getElementsByTagName("test");
        
                Map<String, String> sourceOffset = new HashMap<>();
                sourceOffset.put("sessionId", sessionId);
        
                for (int i = 0; i < tests.getLength(); i++) {
                    if (tests.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element test = (Element) tests.item(i);
                        DirectMeasureData data = new DirectMeasureData(project,sessionId,test.getAttribute("id"),executionDate,test.getAttribute("type"),test.getAttribute("subject"),test.getAttribute("status"),Long.valueOf(test.getAttribute("duration")));
                        result.add(data);
                    }
                }
        
                getProperties().put("lastSession", sessionId);
        
            }
        }
        return result;
    }

}
