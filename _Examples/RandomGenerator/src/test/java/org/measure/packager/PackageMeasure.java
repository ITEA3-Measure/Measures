package org.measure.packager;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnite;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.measure.model.ScopeProperty;
import org.measure.smm.service.MeasurePackager;

public class PackageMeasure {

	public static void main(String[] args) {
		SMMMeasure measure = new SMMMeasure();
		measure.setName("RandomGenerator");
		measure.setDescription("A Test Measure delivering a random number at each execution");
		measure.setType(MeasureType.DIRECT);
		measure.setUnite(MeasureUnite.Numeric);

		ScopeProperty minRange = new ScopeProperty();
		minRange.setName("MinRange");
		minRange.setDescription("Min range of RandomGenerator");
		minRange.setDefaultValue("0");

		ScopeProperty maxRange = new ScopeProperty();
		maxRange.setName("MaxRange");
		maxRange.setDescription("Max range of RandomGenerator");
		maxRange.setDefaultValue("100");
		
		ScopeProperty delta = new ScopeProperty();
		delta.setName("Delta");
		delta.setDescription("Delta max between 2 value ");
		delta.setDefaultValue("5");
		
		ScopeProperty previous = new ScopeProperty();
		previous.setName("PreviousValue");	
		previous.setDefaultValue("0");

		measure.getScopeProperties().add(minRange);
		measure.getScopeProperties().add(maxRange);
		measure.getScopeProperties().add(delta);
		measure.getScopeProperties().add(previous);

		
		try {
			MeasurePackager.packageMeasure(
					new File(
							"C:/Users/aabherve/Desktop/Platform/work/RandomGenerator/target/RandomGenerator-1.0.0.jar")
									.toPath(),
					measure,
					new File("C:/Users/aabherve/Desktop/Platform/work/RandomGenerator/RandomGenerator.zip").toPath());
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}

}
