package org.measure.impl.data;

import java.util.List;
import org.measure.impl.data.RTElement;

public class RTEmbedded {

    private List<RTElement> elements;

    private RTElement type;

    public List<RTElement> getElements() {
        
        return elements;
    }

    public void setElements(List<RTElement> elements) {
        this.elements = elements;
    }

    public RTElement getType() {
        
        return type;
    }

    public void setType(RTElement type) {
        this.type = type;
    }

}
