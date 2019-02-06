package org.measure.impl.data;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.data.RTElement;

@objid ("307ddcf1-6b2a-47c1-9955-bcc93820a619")
public class RTEmbedded {
    @objid ("09386a79-6422-4c9c-8a2b-b74a3eb12564")
    private List<RTElement> elements;

    @objid ("99186b94-28f8-4b1b-8669-700a7fa77909")
    private RTElement type;

    @objid ("28960d08-d7fb-4b28-ae20-d3570d4f4c90")
    public List<RTElement> getElements() {
        
        return elements;
    }

    @objid ("a5b6a89e-4829-40ae-8cbd-8e4d3f16c5ae")
    public void setElements(List<RTElement> elements) {
        this.elements = elements;
    }

    @objid ("620d8c0b-9bf7-48fe-8685-1797b3f0c528")
    public RTElement getType() {
        
        return type;
    }

    @objid ("3e0ee19c-aa10-430c-ba80-ff11d3fb1667")
    public void setType(RTElement type) {
        this.type = type;
    }

}
