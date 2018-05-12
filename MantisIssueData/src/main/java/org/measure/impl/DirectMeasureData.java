package org.measure.impl;

import java.util.Date;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

@objid ("d07c1682-042a-4dfc-9530-06a4c0ba4fca")
public class DirectMeasureData extends DefaultMeasurement {
    @objid ("9c526c0e-b410-490e-a668-394d1ced0c94")
    public void setName(String value) {
        getValues().put("name", value);
    }

    @objid ("5e57492a-138b-4ac7-826c-d2d4ff56341c")
    public void setProject(String value) {
        getValues().put("project", value);
    }

    @objid ("51014d3b-480d-4314-a0c0-6dce1429a851")
    public void setPriority(String value) {
        getValues().put("priority", value);
    }

    @objid ("da1f89e0-6b34-4fad-bdf3-4ae220f40f4c")
    public void setSeverity(String value) {
        getValues().put("severity", value);
    }

    @objid ("362bd36e-2422-4668-b3a7-2d608062e223")
    public void setReproductibility(String value) {
        getValues().put("reproductibility", value);
    }

    @objid ("d478200c-5543-4caa-9906-2639a5c2e5f9")
    public void setStatus(String value) {
        getValues().put("status", value);
    }

    @objid ("6b8d2031-7190-4375-a184-c9df99cf8763")
    public void setOs(String value) {
        getValues().put("os", value);
    }

    @objid ("11e88e06-fabd-440b-be34-9f7203360215")
    public void setPlatform(String value) {
        getValues().put("platform", value);
    }

    @objid ("6f9d74ce-ccde-45a5-bcb4-2ddac6cf402e")
    public void setVersion(String value) {
        getValues().put("version", value);
    }

    @objid ("cc99c821-71b0-4fac-b7df-522d2e3fec15")
    public void setSubmited(Date value) {
        getValues().put("submited", value);
    }

    @objid ("1de43044-2a0b-447b-a46e-316781f1db39")
    public void setLastupdate(Date value) {
        getValues().put("lastupdate", value);
    }

}
