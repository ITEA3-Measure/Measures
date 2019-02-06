package org.measure.restmodel;

import java.util.List;
import org.measure.restmodel.RTPeriod;

public class RTMeasure {

    private String metric;

    private String value;

    private List<RTPeriod> periods;

    public String getMetric() {
        
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getValue() {
        
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<RTPeriod> getPeriods() {
        
        return periods;
    }

    public void setPeriods(List<RTPeriod> periods) {
        this.periods = periods;
    }

}
