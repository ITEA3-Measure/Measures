package org.measure.sonarqube.testsuccessdensity.data;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("625fe0e2-3c89-4bb9-9d0a-4c7bbbb44178")
public class RTHistory {
    @objid ("c052a3a9-4dbc-443e-a7e7-228a19d853c6")
    private String date;

    @objid ("7ed5f2ac-0e3d-4ac8-bd84-762a93108242")
    private String value;

    @objid ("5c6d46db-71e7-4db6-a3de-68e90dbbbc3e")
    public String getDate() {
        
        return date;
    }

    @objid ("8ff714ca-8d4c-4549-b301-e5c5a015b778")
    public void setDate(String date) {
        this.date = date;
    }

    @objid ("8680bb18-ac81-4e6c-9dc0-63df3802483b")
    public String getValue() {
        
        return value;
    }

    @objid ("a929e1a7-c78e-40d2-8e33-e9e72936e9ee")
    public void setValue(String value) {
        this.value = value;
    }

}
