package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("b7da7c4c-5a49-4b1a-8c04-42aa8fbc6f96")
public class RTMeasure {
    @objid ("2bc9d30e-c7d3-4fd7-806c-637147beda4f")
    private String metric;

    @objid ("5db40df2-08a2-44ce-affc-9e5900cf22f8")
    private String value;

    @objid ("7ed9459b-b4b1-4d5b-ae0d-0588ddb5c9ac")
    public String getMetric() {
        
        return metric;
    }

    @objid ("23b6ab93-f647-4862-a2fd-fdeadfb4f766")
    public void setMetric(String metric) {
        this.metric = metric;
    }

    @objid ("1ed7a1b8-9a2f-471d-a93b-c0917339fe64")
    public String getValue() {
        
        return value;
    }

    @objid ("da2d8fd6-cd16-429a-96df-75d9c03ad08f")
    public void setValue(String value) {
        this.value = value;
    }

}
