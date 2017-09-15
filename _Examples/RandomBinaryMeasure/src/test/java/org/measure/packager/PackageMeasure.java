package org.measure.packager;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.measure.smm.measure.model.MeasureReference;
import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnite;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.measure.model.ScopeProperty;
import org.measure.smm.service.MeasurePackager;

public class PackageMeasure {

	
	public static void main(String[] args) {
		SMMMeasure measure = new SMMMeasure();
		measure.setName("RandomBinaryMeasure");
		measure.setDescription("A Test Measure delivering colculating the sum of random numbers");
		measure.setType(MeasureType.BINARY);
		measure.setUnite(MeasureUnite.Numeric);
		
		
		ScopeProperty minRange = new ScopeProperty();
		minRange.setName("Operation");
		minRange.setDescription("Applied Operation");
		minRange.setDefaultValue("+");
		measure.getScopeProperties().add(minRange);
		
		MeasureReference ref1 = new MeasureReference();
		ref1.setExpirationDelay(new Long(60000));
		ref1.setMeasureRef("RandomGenerator");
		ref1.setNumber(1);	
		ref1.setRole("RandomNumber A");
		
		measure.getReferences().add(ref1);
		
		MeasureReference ref2 = new MeasureReference();
		ref2.setExpirationDelay(new Long(60000));
		ref2.setMeasureRef("RandomGenerator");
		ref2.setNumber(1);	
		ref2.setRole("RandomNumber B");
		
		measure.getReferences().add(ref2);
		
		try {
			MeasurePackager.packageMeasure(new File("C:/work/RetD/MeasurePlatform/RandomBinaryMeasure/target/org.measure.smmmeasure.impl.jar").toPath(), measure, new File("C:/work/RetD/MeasurePlatform/RandomBinaryMeasure/RandomBinaryMeasure.zip").toPath());
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}
}
