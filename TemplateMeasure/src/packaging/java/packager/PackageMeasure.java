package packager;

import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;

import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnite;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.service.MeasurePackager;

public class PackageMeasure {
	
	
	public static final String PROJECTSPACE = ...;
	public static final String MEASURENAME = "TemplateMeasure";
	public static void main(String[] args) {
		
		SMMMeasure measure = new SMMMeasure();
		measure.setName(MEASURENAME);
		measure.setDescription("");
		measure.setType(MeasureType.DIRECT);
		measure.setUnite(MeasureUnite.Undefined);


		try {
			MeasurePackager.packageMeasure(Paths.get(PROJECTSPACE +"/target/"+MEASURENAME+"-1.0.0.jar"),Paths.get(PROJECTSPACE +"/lib"),measure,Paths.get(PROJECTSPACE +"/"+MEASURENAME+".zip"));
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}

}
