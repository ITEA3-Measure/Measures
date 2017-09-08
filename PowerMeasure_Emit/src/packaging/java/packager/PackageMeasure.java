package packager;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnite;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.service.MeasurePackager;

public class PackageMeasure {
	
	public static final String PROJECTSPACE = "/home/jerome/GitHub/itea3-measure/Measures/PowerMeasure_Emit";
	
	public static final String MEASURENAME = "PowerMeasure_Emit";
	
	public static final String VERSION = "1.2";

	public static void main(String[] args) throws Exception {
		SMMMeasure measure = new SMMMeasure();
		measure.setName(MEASURENAME);
		measure.setDescription("");
		measure.setType(MeasureType.DIRECT);
		measure.setUnite(MeasureUnite.Numeric);
		Path jar = Paths.get(PROJECTSPACE + "/target/" + MEASURENAME + "-" + VERSION + ".jar");
		Path lib = Paths.get(PROJECTSPACE + "/lib");
		Path zip = Paths.get(PROJECTSPACE + "/" + MEASURENAME + ".zip");
		MeasurePackager.packageMeasure(jar,lib,measure, zip);
	}

}
