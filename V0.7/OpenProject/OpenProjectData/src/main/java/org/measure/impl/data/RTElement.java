package org.measure.impl.data;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.data.RTDescription;
import org.measure.impl.data.RTEmbedded;
import org.measure.impl.data.RTLinks;

@objid ("b87fa708-7395-4b7b-963b-745d443493d3")
public class RTElement {
    @objid ("d2bbc7ad-4453-48aa-b21d-9ec74f7a0b36")
    private String id;

    @objid ("fb5a3363-1fa1-48e8-882e-cd38d669da49")
    private String name;

    @objid ("8f01309f-1ddc-407a-a9ee-fc78541ae829")
    private String subject;

    @objid ("b5da03e8-1378-41e1-82bd-989d51a1fca4")
    private Date createdAt;

    @objid ("565fea72-6877-488b-96cd-1ebb1b180afb")
    private Date updatedAt;

    @objid ("f1688dec-e5a4-4346-9e31-c9be0f704dc7")
    private Date startDate;

    @objid ("957b0f70-7066-4aec-9cdf-6cc46dc5bb0e")
    private Date dueDate;

    @objid ("60ebe5ca-ed30-47e8-933c-6e0fd68ff288")
    private String estimatedTime;

    @objid ("328327b4-cba7-4430-86ec-1e950976e815")
    private String spentTime;

    @objid ("58a02266-29b2-42b3-8e0a-a9f42ea7c238")
    private String remainingTime;

    @objid ("9e5ac6ea-4dc8-40c1-87fb-8da306c552d1")
    private String percentageDone;

    @objid ("f969ce47-16c0-4fc9-851a-1bfcd822d16f")
    private RTDescription description;

    @objid ("8ed66479-8f7a-45be-a34e-df4396c56e96")
    private RTEmbedded _embedded;

    @objid ("857297b4-0228-46e6-8a58-e9e8e1b36986")
    private RTLinks _links;

    @objid ("c48345ce-509e-4892-b6eb-55438e8b5426")
    public String getSubject() {
        
        return subject;
    }

    @objid ("b23cb2a3-8746-44b6-bc17-95f00b2e5dda")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @objid ("c66a0330-4c1c-42ad-9046-61334ac18638")
    public RTDescription getDescription() {
        
        return description;
    }

    @objid ("a2fa275a-a34c-4f7e-b6be-32712202c686")
    public void setDescription(RTDescription description) {
        this.description = description;
    }

    @objid ("60dbb6fc-d78b-4639-ac7e-44b0505882fa")
    public RTEmbedded get_embedded() {
        
        return _embedded;
    }

    @objid ("48d2618f-c319-48cd-9713-af3fd2f30b35")
    public void set_embedded(RTEmbedded _embedded) {
        this._embedded = _embedded;
    }

    @objid ("73468a12-6a49-49a2-b07f-dfdaf0fbccbf")
    public RTLinks get_links() {
        
        return _links;
    }

    @objid ("4d744dcb-cdde-4e4d-a52b-0316192a08ba")
    public void set_links(RTLinks _links) {
        this._links = _links;
    }

    @objid ("505181a8-2c9e-4edf-8c2f-9433f6b11a86")
    public Date getCreatedAt() {
        
        return createdAt;
    }

    @objid ("25593e2d-9326-485c-8121-9a7c3443f6fc")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @objid ("fa417ae2-c1e1-4dea-9366-690cea0097a8")
    public Date getUpdatedAt() {
        
        return updatedAt;
    }

    @objid ("14dcecd4-aeea-447a-8cc8-b44ba94ad216")
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @objid ("ee21f36a-361b-48fb-ac59-138c5e7f10ab")
    public Date getStartDate() {
        
        return startDate;
    }

    @objid ("6cdf3e80-fe39-4409-9497-cb7294f4bfac")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @objid ("b1d266dc-5dfe-40f0-a4b5-9c87e9111606")
    public Date getDueDate() {
        
        return dueDate;
    }

    @objid ("6f8fcefa-af2d-4371-b8e8-d8c393b90346")
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @objid ("8b346a4d-0581-4df2-b944-a2252bc8f9d2")
    public String getEstimatedTime() {
        
        return estimatedTime;
    }

    @objid ("978b08b3-441a-47ef-b442-9e64f9e1563d")
    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    @objid ("0638869e-f1d3-44bc-9b34-0ed2f768adf6")
    public String getSpentTime() {
        
        return spentTime;
    }

    @objid ("6d77e8db-6d02-4be4-ace8-813046ef31e8")
    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    @objid ("87b43e32-dac2-4d63-b161-6b1b8f048667")
    public String getRemainingTime() {
        
        return remainingTime;
    }

    @objid ("fbeba487-3d83-4834-8e66-f3ca7be504d9")
    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    @objid ("c48fcf1f-5f46-4f49-be98-05d818fd3c88")
    public String getPercentageDone() {
        
        return percentageDone;
    }

    @objid ("867f406b-b7ad-4047-8218-8c545204a6da")
    public void setPercentageDone(String percentageDone) {
        this.percentageDone = percentageDone;
    }

    @objid ("45e9a53b-8f8c-4022-90ff-f7e60c5913c4")
    public String getId() {
        
        return id;
    }

    @objid ("dc8788ec-9abe-4058-beb7-97ffcf9b22cf")
    public void setId(String id) {
        this.id = id;
    }

    @objid ("0d969419-42aa-4a92-84d3-633e840306ba")
    public String getName() {
        
        return name;
    }

    @objid ("8475ab05-c447-4b46-a639-3f1d24e6eeb3")
    public void setName(String name) {
        this.name = name;
    }

}
