package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d92ac462-bf4b-493a-a6b7-c7c68e67105e")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("f93b64c7-9391-4b03-a827-882873836e8a")
    public DirectMeasureData() {
        // TODO Auto-generated constructor stub
    }

    @objid ("7427151a-076a-45e4-8a53-c9d40758f41d")
    public void setAllIssuesCount(Integer number) {
        getValues().put("allIssues", number);
    }

    @objid ("8a237d9b-7dba-498a-b9cf-7445f2c86511")
    public void setOpenIssuesCount(Integer number) {
        getValues().put("openIssues", number);
    }

    @objid ("0335770b-49de-483d-874b-102bc4e46cba")
    public void setNewIssueCount(Integer number) {
        getValues().put("newIssue", number);
    }

    @objid ("eaa60ddf-838b-4427-a33b-130b496fe58e")
    public void setFeedbackIssueCount(Integer number) {
        getValues().put("feedbackIssue", number);
    }

    @objid ("7d96cf45-567a-4745-9ded-86fc8be6bf65")
    public void setAcknowledgedIssueCount(Integer number) {
        getValues().put("acknowledgedIssue", number);
    }

    @objid ("5de96998-b648-4f40-b5ad-f357e9a59787")
    public void setConfirmedIssueCount(Integer number) {
        getValues().put("confirmedIssue", number);
    }

    @objid ("6b729c06-f801-4d77-8601-6eda92c9faa6")
    public void setAssignedIssueCount(Integer number) {
        getValues().put("assignedIssue", number);
    }

    @objid ("08a6cdab-cd8d-4e7a-8a4f-659ba99d14e4")
    public void setResolvedIssueCount(Integer number) {
        getValues().put("resolvedIssue", number);
    }

    @objid ("eaf5dd25-4532-462b-a45a-01b81e98d954")
    public void setClosedIssueCount(Integer number) {
        getValues().put("closedIssue", number);
    }

}
