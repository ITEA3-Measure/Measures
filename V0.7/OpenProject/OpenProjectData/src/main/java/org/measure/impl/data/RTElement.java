package org.measure.impl.data;

import java.util.Date;
import org.measure.impl.data.RTDescription;
import org.measure.impl.data.RTEmbedded;
import org.measure.impl.data.RTLinks;

public class RTElement {

    private String id;

    private String name;

    private String subject;

    private Date createdAt;

    private Date updatedAt;

    private Date startDate;

    private Date dueDate;

    private String estimatedTime;

    private String spentTime;

    private String remainingTime;

    private String percentageDone;

    private RTDescription description;

    private RTEmbedded _embedded;

    private RTLinks _links;

    public String getSubject() {
        
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public RTDescription getDescription() {
        
        return description;
    }

    public void setDescription(RTDescription description) {
        this.description = description;
    }

    public RTEmbedded get_embedded() {
        
        return _embedded;
    }

    public void set_embedded(RTEmbedded _embedded) {
        this._embedded = _embedded;
    }

    public RTLinks get_links() {
        
        return _links;
    }

    public void set_links(RTLinks _links) {
        this._links = _links;
    }

    public Date getCreatedAt() {
        
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getStartDate() {
        
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getEstimatedTime() {
        
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getSpentTime() {
        
        return spentTime;
    }

    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    public String getRemainingTime() {
        
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getPercentageDone() {
        
        return percentageDone;
    }

    public void setPercentageDone(String percentageDone) {
        this.percentageDone = percentageDone;
    }

    public String getId() {
        
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
