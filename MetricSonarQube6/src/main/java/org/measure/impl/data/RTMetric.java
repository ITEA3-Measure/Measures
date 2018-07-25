package org.measure.impl.data;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("80abbde5-74fb-449b-8955-45771c1be527")
public class RTMetric {
    @objid ("f327dcbd-e725-43c4-85aa-d8c8b52e0f18")
    private RTComponent component;

    @objid ("329f8227-2fea-40d5-8917-dc40521ff379")
    private List<RTError> errors;

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

    @objid ("b1062c79-8da3-4e5b-a35d-66320b41df5c")
    public List<RTError> getErrors() {
        
        return errors;
    }

    @objid ("b117b690-7a64-424e-8768-dd5dfa663ed9")
    public void setErrors(List<RTError> errors) {
        this.errors = errors;
    }

}
