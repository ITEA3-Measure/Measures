package org.measure.impl;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DirectMeasureData extends DefaultMeasurement {

    public DirectMeasureData() {
        // TODO Auto-generated constructor stub
    }

    public void setAllIssuesCount(Integer number) {
        getValues().put("allIssues", number);
    }

    public void setOpenIssuesCount(Integer number) {
        getValues().put("openIssues", number);
    }

    public void setNewIssueCount(Integer number) {
        getValues().put("newIssue", number);
    }

    public void setFeedbackIssueCount(Integer number) {
        getValues().put("feedbackIssue", number);
    }

    public void setAcknowledgedIssueCount(Integer number) {
        getValues().put("acknowledgedIssue", number);
    }

    public void setConfirmedIssueCount(Integer number) {
        getValues().put("confirmedIssue", number);
    }

    public void setAssignedIssueCount(Integer number) {
        getValues().put("assignedIssue", number);
    }

    public void setResolvedIssueCount(Integer number) {
        getValues().put("resolvedIssue", number);
    }

    public void setClosedIssueCount(Integer number) {
        getValues().put("closedIssue", number);
    }

}
