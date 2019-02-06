package org.measure.restmodel;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("f07f3a7b-af53-40af-873d-3cd880d50dbc")
public class RTResponse {
    @objid ("1752c84c-2774-463d-9dae-b59582f2f018")
    private RTComponent component;

    @objid ("65567597-e0aa-4f81-88aa-5c37f195c8cb")
    public RTComponent getComponent() {
        
        return component;
    }

    @objid ("aed6ba9f-20f7-461c-a23a-5b7de4361f43")
    public void setComponent(RTComponent component) {
        this.component = component;
    }

}
