package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("850d9546-021c-4271-be93-0b3ea4026f62")
public class RTComponent {
    @objid ("65db7e17-9f47-4ff5-b197-bc843c3d4f3d")
    private List<RTMeasure> measures = new ArrayList<>();

    @objid ("2ec0b2be-e0ad-4bad-a834-8eac6b350b2a")
    public List<RTMeasure> getMeasures() {
        
        return measures;
    }

    @objid ("6d49f9d6-6a05-4218-b4cf-5e3b64b56f58")
    public void setMeasures(List<RTMeasure> measures) {
        this.measures = measures;
    }

    @objid ("c104e4ce-82e8-41e1-95ca-4c44d7ff7e7c")
    public String getValue() {
        if (measures != null && measures.size() > 0)
            return measures.get(0).getValue();
        return "";
    }

}
