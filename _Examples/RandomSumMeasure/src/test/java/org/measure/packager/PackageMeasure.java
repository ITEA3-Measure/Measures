package org.measure.packager;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.measure.smm.measure.model.MeasureReference;
import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnite;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.service.MeasurePackager;


public class PackageMeasure {

	
	public static void main(String[] args) {
		SMMMeasure measure = new SMMMeasure();
		measure.setName("RandomSumMeasure");
		measure.setDescription("A Test Measure delivering colculating the sum of random numbers");
		measure.setType(MeasureType.COLLECTIVE);
		measure.setUnite(MeasureUnite.Numeric);
		
		MeasureReference ref = new MeasureReference();
		ref.setExpirationDelay(new Long(60000));
		ref.setMeasureRef("RandomGenerator");
		ref.setNumber(10);	
		ref.setRole("RandomNumber");
		
		measure.getReferences().add(ref);
		
		try {
			MeasurePackager.packageMeasure(new File("C:/work/RetD/MeasurePlatform/RandomSumMeasure/target/org.measure.smmmeasure.impl.jar").toPath(), measure, new File("C:/work/RetD/MeasurePlatform/RandomSumMeasure/RandomSumMeasure.zip").toPath());
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}


}
