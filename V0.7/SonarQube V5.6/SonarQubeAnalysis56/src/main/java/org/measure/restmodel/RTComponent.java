package org.measure.restmodel;

import java.util.List;
import org.measure.restmodel.RTMeasure;

public class RTComponent {
    private List<RTMeasure> measures;

    public List<RTMeasure> getMeasures() {
        
        return measures;
    }

    public void setMeasures(List<RTMeasure> measures) {
        this.measures = measures;
    }

}
