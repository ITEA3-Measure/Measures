package org.measure.restmodel;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("8bc020eb-4bf8-4ddb-a502-16ac49b2efee")
public class RTPeriod {
    @objid ("37a94800-c2b9-447a-8902-01e336a4e5b6")
    public int index;

    @objid ("34449684-86b2-400d-bab4-9ee0efa1fc86")
    public String value;

    @objid ("09d8e23b-667f-4e99-a0b0-42edb5a2487f")
    public int getIndex() {
        
        return index;
    }

    @objid ("8778046e-f365-4e73-920b-fce42ca2b1c3")
    public void setIndex(int index) {
        this.index = index;
    }

    @objid ("0d4aed58-a5ec-46f1-9fe1-e8a03a57edfe")
    public String getValue() {
        
        return value;
    }

    @objid ("866cbcc6-333c-457f-b0d6-5931e42d09dd")
    public void setValue(String value) {
        this.value = value;
    }

}
