package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("80abbde5-74fb-449b-8955-45771c1be527")
public class RTMetric {
    @objid ("f327dcbd-e725-43c4-85aa-d8c8b52e0f18")
    private RTComponent component;

    @objid ("76f922cc-d3b7-447c-8601-1b3d9d4e94bb")
    public RTComponent getComponent() {
        
        return component;
    }

    @objid ("e4190d5b-4542-4baa-94f5-9fe73183767b")
    public void setComponent(RTComponent component) {
        this.component = component;
    }

    @objid ("ed0828d9-c012-433b-964d-7d997b1b8b1f")
    public String getValue() {
        if (component != null)
            return component.getValue();
        return "";
    }

}
