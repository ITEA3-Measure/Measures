package org.measure.impl.data;

import java.util.List;
import org.measure.impl.data.RTBuild;

public class RTJob {

    private String _class;

    private String name;

    private String url;

    private List<RTBuild> builds;

    public String get_class() {
        
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getName() {
        
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<RTBuild> getBuilds() {
        
        return builds;
    }

    public void setBuilds(List<RTBuild> builds) {
        this.builds = builds;
    }

}
