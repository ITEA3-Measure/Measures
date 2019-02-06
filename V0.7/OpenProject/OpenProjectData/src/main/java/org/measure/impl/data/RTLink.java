package org.measure.impl.data;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("752ef941-6b3a-4f82-8c4d-c6c7486605a0")
public class RTLink {
    @objid ("ce6d08ec-61d0-4d67-a42f-b608d1d30579")
    private String href;

    @objid ("3d7f8617-002a-4e44-a0ae-ec1abde430a2")
    private String title;

    @objid ("5b4343ff-005a-4bad-9581-6dc8d069712a")
    public String getHref() {
        
        return href;
    }

    @objid ("f9cb7d01-faa8-4423-b813-097e7a228f9b")
    public void setHref(String href) {
        this.href = href;
    }

    @objid ("86362543-b281-404f-834e-1331e0c721dd")
    public String getTitle() {
        
        return title;
    }

    @objid ("4527abf3-f368-416a-bfad-a7b63576be22")
    public void setTitle(String title) {
        this.title = title;
    }

}
