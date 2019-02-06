package org.measure.restmodel;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("93ffc5a6-11be-4912-a091-7a7de4ad7561")
public class RTPeriod {
    @objid ("1e0f04c6-e801-4d0a-8f61-64f6d81f1f1f")
    public int index;

    @objid ("601ac686-12e3-4b86-945e-4b276f425779")
    public String value;

    @objid ("510e3a1d-0026-43ed-b0ff-d32e03acd4ff")
    public int getIndex() {
        
        return index;
    }

    @objid ("eaee35e4-8119-4fc8-8165-484c7b86b8cc")
    public void setIndex(int index) {
        this.index = index;
    }

    @objid ("afffb120-0d2b-423c-b6be-c8e29d034768")
    public String getValue() {
        
        return value;
    }

    @objid ("db1c76a8-882e-4e4f-8c65-0440c19717fc")
    public void setValue(String value) {
        this.value = value;
    }

}
