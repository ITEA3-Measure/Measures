package org.measure.sonarqube.branchcoverage.hitsdata.data;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("a0650ea2-0f86-4c0e-a540-70163b0e2072")
public class RTMetric {
    @objid ("7abb8f4e-8284-42f8-af86-e51b66987796")
    private List<RTMeasure> measures = new ArrayList<>();

    @objid ("0a5b8bf0-d239-44e9-b6cf-77df64a71b39")
    private List<RTError> errors;

    @objid ("d3230043-e649-40ab-b8ca-683439f69ea6")
    public List<RTMeasure> getMeasures() {
        
        return measures;
    }

    @objid ("96cd5da3-b7f2-4491-9d8d-98803d5b9bb2")
    public void setMeasures(List<RTMeasure> measures) {
        this.measures = measures;
    }

    @objid ("048c6999-5bee-4c35-b45e-a5471eb13d1f")
    public List<RTError> getErrors() {
        
        return errors;
    }

    @objid ("c458628f-c5d1-4f9e-8197-9195814a2ac9")
    public void setErrors(List<RTError> errors) {
        this.errors = errors;
    }

}
