package org.measure.impl;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d3fceec1-e15e-4429-af99-3334e1536264")
public class GitHubMeasurement extends DefaultMeasurement {
    @objid ("97bc5488-dec3-4dd9-a549-3ab3817dc4fc")
    public GitHubMeasurement(String author, Date date, String message, Integer ligneAdd, Integer ligneChanged, Integer ligneDelete) {
        getValues().put("author", author);
        getValues().put("postDate", date);
        getValues().put("commitMessage", message);
        getValues().put("LigneAdd", ligneAdd);
        getValues().put("LigneChanged", ligneChanged);
        getValues().put("LigneDelete", ligneDelete);
    }

    @objid ("6c503794-5ed6-46d7-9acb-33c7301ee65a")
    public String getLabel() {
        
        return "[Author]: " + getValues().get("author") + "  [date]: " + getValues().get("postDate") + "[message]: "
                                + getValues().get("commitMessage");
    }

}
