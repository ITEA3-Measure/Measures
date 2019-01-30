package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DerivedMeasure;

@objid ("948d0df6-9fe2-4a4c-b4c0-83c644d49a3a")
public class DerivedMeasureImpl extends DerivedMeasure {
    @objid ("658ab455-5581-4c1d-a554-ecdab54898a4")
    @Override
    public List<IMeasurement> calculateMeasurement() throws Exception {
        Integer result = 0;
        for (IMeasurement operande : getMeasureInputByRole("RandomNumber")) {
            try {
                result = result + (Integer) operande.getValues().get("value");
            } catch (NumberFormatException e) {
                System.out.println("Non Numeric Operande");
            }
        }
        
        IntegerMeasurement measurement = new IntegerMeasurement();
        measurement.setValue(result);
        
        List<IMeasurement> measurements = new ArrayList<>();
        measurements.add(measurement);
        return measurements;
    }

}
