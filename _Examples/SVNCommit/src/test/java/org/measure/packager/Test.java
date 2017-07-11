package org.measure.packager;

import java.util.Date;
import java.util.HashMap;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.svn.commit.SVNCommitImpl;

public class Test {

	public static void main(String[] args) {
		SVNCommitImpl measure = new SVNCommitImpl();
		measure.getProperties().put("URI", "https://rd.constellation.modeliosoft.com/svn/e0a5faac-e895-4dab-a047-de2b7a1f1110/trunk");
		measure.getProperties().put("PASSWORD", "aab");
		measure.getProperties().put("LOGIN", "aab");
		measure.getProperties().put("LastUpdate", String.valueOf(new Date().getTime()));
			

		try {
			for(IMeasurement result : measure.getMeasurement()){
				System.out.println(result.getValues().get("Date") + " [" +result.getValues().get("Author") +"] :" + result.getValues().get("Message"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
