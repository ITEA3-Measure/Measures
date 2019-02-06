package org.measure.sonarqube.uncoveredlines.data;

import java.util.ArrayList;
import java.util.List;

public class RTMetric {
    private List<RTMeasure> measures = new ArrayList<>();

    private List<RTError> errors;

    public List<RTMeasure> getMeasures() {
        
        return measures;
    }

    public void setMeasures(List<RTMeasure> measures) {
        this.measures = measures;
    }

    public List<RTError> getErrors() {
        
        return errors;
    }

    public void setErrors(List<RTError> errors) {
        this.errors = errors;
    }

}
