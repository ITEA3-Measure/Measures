package org.measure.resttemplatemodel;


public class RTElement {
	
    private String id;

    private String name;

    private String estimatedTime;

    private String spentTime;

    private String remainingTime;

    private RTLinks _links;


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

	public RTLinks get_links() {
		return _links;
	}

	public void set_links(RTLinks _links) {
		this._links = _links;
	}
}
