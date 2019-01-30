package org.measure.impl;

import java.util.Map.Entry;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("fc2e5d5d-fc09-441b-aa2f-12cb6be74677")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("c65c1926-842f-42b9-96d8-3baffcd2629c")
    public DirectMeasureData() {
        
    }

    @objid ("523f532f-4568-4272-9680-bed37c898cdc")
    public void addValue(String type, Double value) {
        super.addValue(type,value);
    }

    @objid ("a0db99e2-dc8b-4488-a3ab-3345684a056b")
    @Override
    public String getLabel() {
        StringBuffer buffer = new StringBuffer();
        for(Entry<String,Object> entry : getValues().entrySet()){
            buffer.append(entry.getKey());
            buffer.append(" : ");
            buffer.append(entry.getValue());
            buffer.append(",");
        }
        return buffer.toString();
    }

}
