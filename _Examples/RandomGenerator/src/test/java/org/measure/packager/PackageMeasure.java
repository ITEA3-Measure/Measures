package org.measure.packager;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.measure.smm.measure.model.FieldType;
import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnit;
import org.measure.smm.measure.model.MeasureUnitField;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.measure.model.ScopeProperty;
import org.measure.smm.measure.model.ScopePropertyType;
import org.measure.smm.service.MeasurePackager;

public class PackageMeasure {

	public static void main(String[] args) {
		SMMMeasure measure = new SMMMeasure();
		measure.setName("RandomGenerator");
		measure.setDescription("A Test Measure delivering a random number at each execution");
		measure.setType(MeasureType.DIRECT);
		measure.setCategory("Test");
		measure.setProvider("Softeam");
		
		MeasureUnit unit = new MeasureUnit();
		MeasureUnitField f1 = new MeasureUnitField();
		f1.setFieldName("postDate");
		f1.setFieldType(FieldType.u_date);
		unit.getFields().add(f1);
		
		MeasureUnitField f2 = new MeasureUnitField();
		f2.setFieldName("myValue");
		f2.setFieldType(FieldType.u_double);
		unit.getFields().add(f2);
		
		measure.setUnit(unit);

		ScopeProperty minRange = new ScopeProperty();
		minRange.setName("MinRange");
		minRange.setDescription("Min range of RandomGenerator");
		minRange.setDefaultValue("0");
		minRange.setType(ScopePropertyType.INTEGER);

		ScopeProperty maxRange = new ScopeProperty();
		maxRange.setName("MaxRange");
		maxRange.setDescription("Max range of RandomGenerator");
		maxRange.setDefaultValue("100");
		maxRange.setType(ScopePropertyType.INTEGER);
		
		ScopeProperty delta = new ScopeProperty();
		delta.setName("Delta");
		delta.setDescription("Delta max between 2 value ");
		delta.setDefaultValue("5");
		delta.setType(ScopePropertyType.INTEGER);
		
		ScopeProperty previous = new ScopeProperty();
		previous.setName("PreviousValue");	
		previous.setDefaultValue("0");
		previous.setType(ScopePropertyType.DESABLE);

		measure.getScopeProperties().add(minRange);
		measure.getScopeProperties().add(maxRange);
		measure.getScopeProperties().add(delta);
		measure.getScopeProperties().add(previous);

		
		try {
			MeasurePackager.packageMeasure(
					new File("C:/work/MEASURE/MeasureDevelopement/workspace/SMMMeasureLibrary/Measures/_Examples/RandomGenerator/target/RandomGenerator-1.0.0.jar").toPath(),
					new File("C:/work/MEASURE/MeasureDevelopement/workspace/SMMMeasureLibrary/Measures/_Examples/RandomGenerator/lib/").toPath(),
					measure,
					new File("C:/work/MEASURE/MeasureDevelopement/workspace/SMMMeasureLibrary/Measures/_Examples/RandomGenerator/RandomGenerator.zip").toPath());
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}

}
