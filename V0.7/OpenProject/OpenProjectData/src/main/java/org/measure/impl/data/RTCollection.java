package org.measure.impl.data;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.impl.data.RTEmbedded;

@objid ("3db487fc-6c42-449a-ac05-32009609a29c")
public class RTCollection {
    @objid ("0c1b41a7-ff82-479d-a81b-7e3cdf7a7a82")
    private RTEmbedded _embedded;

    @objid ("6eab8ef8-e683-4100-ac32-bc3b9b33cdb0")
    public RTEmbedded get_embedded() {
        
        return _embedded;
    }

    @objid ("472462f6-23b9-4a1d-bc27-6ca9d5d33276")
    public void set_embedded(RTEmbedded _embedded) {
        this._embedded = _embedded;
    }

}
