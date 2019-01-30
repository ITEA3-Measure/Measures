package org.measure.impl;

import java.util.Date;
import java.util.HashMap;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("a77c9fbd-a9a7-451d-826c-50c0ff376f11")
public class SVNTrackerData extends DefaultMeasurement {
    @objid ("e0c8640c-8ca7-46fa-b9a5-cf1393830d8a")
    public SVNTrackerData(String author, String message, Date postDate) {
        super();
        this.valueMap = new HashMap<>();
        if(author == null){
            author = "";
        }
        if(message == null){
            message = "";
        }
        if(postDate == null){
            postDate = new Date();
        }
        this.valueMap.put("Author", author);
        this.valueMap.put("Message", message);
        
        
        this.valueMap.put("postDate",new Date(postDate.getTime()));
    }

    @objid ("5e5f27ea-2981-4a90-8ea6-aed603c0bbb8")
    @Override
    public String getLabel() {
        
        return valueMap.get("postDate")+ " [" + valueMap.get("Author") + "]: " +  valueMap.get("Message");
    }

}
