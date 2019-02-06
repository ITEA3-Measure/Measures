package org.measure.restmodel;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.restmodel.RTPeriod;

@objid ("cbd2f1db-1460-4c38-b094-d6dd2bd44a36")
public class RTMeasure {
    @objid ("b38e76cd-c0aa-4bbb-97bf-c86f6557ef4a")
    private String metric;

    @objid ("3a7a2844-5e07-48e1-b08b-d75be6fafeb5")
    private String value;

    @objid ("97d3a945-dedc-4e0a-862c-5e7933a03902")
    private List<RTPeriod> periods;

    @objid ("82686bb3-8156-4b8b-8735-1e3ec307ebd5")
    public String getMetric() {
        
        return metric;
    }

    @objid ("010b20cc-d419-44a5-bdf0-0816e99b49f2")
    public void setMetric(String metric) {
        this.metric = metric;
    }

    @objid ("3ee54be6-46cf-4a43-a93b-f36065206fc7")
    public String getValue() {
        
        return value;
    }

    @objid ("633ddde5-f65e-4375-88d6-e9773b19bd2d")
    public void setValue(String value) {
        this.value = value;
    }

    @objid ("6e69a0ac-f9ec-4055-8438-23117b72b90f")
    public List<RTPeriod> getPeriods() {
        
        return periods;
    }

    @objid ("8c231287-c3c1-4e94-aa48-86049e3d3705")
    public void setPeriods(List<RTPeriod> periods) {
        this.periods = periods;
    }

}
