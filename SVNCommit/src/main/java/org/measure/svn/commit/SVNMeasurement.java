package org.measure.svn.commit;

import java.util.Date;
import java.util.HashMap;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class SVNMeasurement extends DefaultMeasurement {

		
	public SVNMeasurement(String author,String message,Date postDate){
		super();
		this.valueMap = new HashMap<>();
		if(author == null){
			author = "";
		}
		if(message == null){
			message = "";
		}
		if(postDate == null){
			postDate = new Date();
		}
		this.valueMap.put("Author", author);
		this.valueMap.put("Message", message);
		
		
		this.valueMap.put("postDate",new Date(postDate.getTime()));

	}



	@Override
	public String getLabel() {
		return valueMap.get("postDate")+ " [" + valueMap.get("Author") + "]: " +  valueMap.get("Message");
	}
}
