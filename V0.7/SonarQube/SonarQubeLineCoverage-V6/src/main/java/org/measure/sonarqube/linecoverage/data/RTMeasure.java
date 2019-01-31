package org.measure.sonarqube.linecoverage.data;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("15dbfe7f-c2d6-43bd-a972-528ed163df55")
public class RTMeasure {
    @objid ("1b95a2f2-f77a-47bb-abaf-0722eaa946cc")
    private List<RTHistory> history;

    @objid ("320b8406-991f-4a41-a4be-3cf18124931a")
    public List<RTHistory> getHistory() {
        
        return history;
    }

    @objid ("3923a04f-db78-4ac7-98e0-c4fbad347a10")
    public void setHistory(List<RTHistory> history) {
        this.history = history;
    }

}
