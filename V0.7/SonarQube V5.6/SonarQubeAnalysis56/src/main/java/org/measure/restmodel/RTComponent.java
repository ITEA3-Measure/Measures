package org.measure.restmodel;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.restmodel.RTMeasure;

@objid ("6b72a845-44c8-4007-98e8-60a754eef8d2")
public class RTComponent {
    @objid ("309e3663-069e-4de0-b890-f6fad1024795")
    private List<RTMeasure> measures;

    @objid ("4c48bcdc-9ea8-4ab5-8f6f-960966c0ce90")
    public List<RTMeasure> getMeasures() {
        
        return measures;
    }

    @objid ("209e2f3d-ec41-4a1e-a59f-96104f668bb7")
    public void setMeasures(List<RTMeasure> measures) {
        this.measures = measures;
    }

}
