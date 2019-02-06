package org.measure.sonarqube.securityremediationeffort.data;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("6619a4d3-919e-46fc-85ef-d6e365a0c87c")
public class RTError {
    @objid ("3b4123da-c01c-484d-8d29-4a0ec6df6a09")
    private String msg;

    @objid ("85f6035a-8d89-491e-9ac1-41771d119d84")
    public String getMsg() {
        
        return msg;
    }

    @objid ("ad12f374-fda7-47e9-bbbe-4eee4361a278")
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
