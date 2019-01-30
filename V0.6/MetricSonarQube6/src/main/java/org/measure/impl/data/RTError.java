package org.measure.impl.data;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("1bcf74d0-cf26-4fbf-909c-3a8c3cce8f74")
public class RTError {
    @objid ("3f9274d6-e142-408e-ae72-544081195a29")
    private String msg;

    @objid ("15546aaa-0cf4-430f-a0df-56ed7148babd")
    public String getMsg() {
        
        return msg;
    }

    @objid ("b241841c-cba4-48d2-ac97-0f0436746d70")
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
