package org.measure.restmodel;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("85258499-f3d5-4789-b8e0-087521797fd9")
public class RTComponent {
    @objid ("d33bd44a-5f9d-42b6-b6be-c13b99b57abd")
    private List<RTMeasure> measures;

    @objid ("f0686338-0862-426a-a5da-dd605692d055")
    public List<RTMeasure> getMeasures() {
        
        return measures;
    }

    @objid ("fdc3ac82-29d5-4fb9-b2e8-c4fabb084b8f")
    public void setMeasures(List<RTMeasure> measures) {
        this.measures = measures;
    }

}
