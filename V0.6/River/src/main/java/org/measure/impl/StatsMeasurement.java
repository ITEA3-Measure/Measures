package org.measure.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

import java.util.Map;

//class Stats {
//  int corpus;
//  int traced;
//  int passed;
//  int failed;
//  int errored;
//}

public class StatsMeasurement extends DefaultMeasurement {
    public StatsMeasurement() {

    }

    public void setValue(Stats s, String id, String name) {
        addValue("id", id);
        addValue("corpus", s.corpus);
        addValue("traced", s.traced);
        addValue("passed", s.passed);
        addValue("failed", s.failed);
        addValue("errored", s.errored);
        addValue("coverage", s.coverage);
        addValue("name", name);
    }

    public Stats getValue() {
        Stats s = new Stats();
        Map<String, Object> res = getValues();
        s.corpus = (int) res.get("corpus");
        s.traced = (int) res.get("traced");
        s.passed = (int) res.get("passed");
        s.failed = (int) res.get("failed");
        s.errored = (int) res.get("errored");
        return s;
    }

    @Override
    public String getLabel() {
        return getValues().get("id").toString();
    }

}
