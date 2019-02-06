package org.measure.restmodel;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.restmodel.RTPeriod;

@objid ("2b2ecad2-c5bf-4e18-84a2-941f5b33a9bf")
public class RTMeasure {
    @objid ("b647beee-7909-4052-97b2-901d9f06b555")
    private String metric;

    @objid ("aa89ad46-893c-4cb3-84e1-46e4c53524bb")
    private String value;

    @objid ("e84563d7-b617-4bf9-96b9-d12dbca616b5")
    private List<RTPeriod> periods;

    @objid ("cd323f95-c1ad-4a81-b0dd-72a9b4c56159")
    public String getMetric() {
        
        return metric;
    }

    @objid ("6fa6ae79-c7ca-495e-b03d-c80b020b02e0")
    public void setMetric(String metric) {
        this.metric = metric;
    }

    @objid ("c202249a-1eb8-4c28-aec9-8e15024f092c")
    public String getValue() {
        
        return value;
    }

    @objid ("7e9b869d-7e0e-40d6-ad9b-47f67ac1a75e")
    public void setValue(String value) {
        this.value = value;
    }

    @objid ("bf2e3508-00f3-4a90-9133-174ada49a422")
    public List<RTPeriod> getPeriods() {
        
        return periods;
    }

    @objid ("5ec04914-3b85-4ff8-abe6-05cb5c62706f")
    public void setPeriods(List<RTPeriod> periods) {
        this.periods = periods;
    }

}
