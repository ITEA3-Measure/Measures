package org.measure.sonarqube.branchcoverage.data;

import java.util.List;

public class RTMeasure {
    private List<RTHistory> history;

    public List<RTHistory> getHistory() {
        
        return history;
    }

    public void setHistory(List<RTHistory> history) {
        this.history = history;
    }

}
