package org.measure.restmodel;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("1799db62-c247-47c4-a585-81b2220029a4")
public class RTResponse {
    @objid ("6d83d8d6-1c4b-469f-a294-8487d033aaa7")
    private RTComponent component;

    @objid ("749659af-2da5-49f7-8d50-f5b1c02ee31a")
    public RTComponent getComponent() {
        
        return component;
    }

    @objid ("62aef7df-0066-4958-8b09-b3859bf27ca4")
    public void setComponent(RTComponent component) {
        this.component = component;
    }

}
