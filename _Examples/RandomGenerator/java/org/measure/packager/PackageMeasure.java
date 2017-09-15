package org.measure.packager;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.measure.smmmeasure.model.MeasureReference;
import org.measure.smmmeasure.model.MeasureType;
import org.measure.smmmeasure.model.MeasureUnite;
import org.measure.smmmeasure.model.SMMMeasure;
import org.measure.smmmeasure.service.MeasurePackager;

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
	
//	public static void main(String[] args) {
//	SMMMeasure measure = new SMMMeasure();
//	measure.setName("RandomGenerator");
//	measure.setDescription("A Test Measure delivering a random number at each execution");
//	measure.setType(MeasureType.DIRECT);
//	measure.setUnite(MeasureUnite.Numeric);
//	
//	ScopeProperty minRange = new ScopeProperty();
//	minRange.setName("MinRange");
//	minRange.setDescription("Min range of RandomGenerator");
//	minRange.setDefaultValue("0");
//	
//	ScopeProperty maxRange = new ScopeProperty();
//	maxRange.setName("MaxRange");
//	maxRange.setDescription("Max range of RandomGenerator");
//	maxRange.setDefaultValue("100");
//	
//	measure.getScopeProperties().add(minRange);
//	measure.getScopeProperties().add(maxRange);
//	
//	try {
//		MeasurePackager.packageMeasure(new File("C:/Users/aabherve/Desktop/Platform/work/RandomGenerator/target/org.measure.smmmeasure.impl.jar").toPath(), measure, new File("C:/Users/aabherve/Desktop/Platform/work/RandomGenerator/RandomGenerator.zip").toPath());
//	} catch (JAXBException | IOException e) {
//		e.printStackTrace();
//	}
//}

}
