package org.measure.mmtplugin.logics;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import java.util.Arrays;
import org.bson.Document;
import org.measure.mmtplugin.MMTMeasurement;

public class MMTSecurityIncidents extends MMTLogic{

    @Override
    public MMTMeasurement computeMetrics(MongoCollection collection) {
        // Determine the timeslice to analyze
        // TODO - At the moment this means "all".
        
        // Query the collection
        // Get the total amount of properties detected as "attack" or "security"
        // This query will return the amount of instances per property violated
        
        // Get the timestamp of the last COMPLETED minute and rest 60.000 ms
        long actualTime =  System.currentTimeMillis();
        //long actualTime =  1504098147181;
        double maxTstamp = actualTime - (actualTime % 10000);
        
        MongoCursor<Document> cursor = collection.aggregate(
                Arrays.asList(
                        Aggregates.match(
                                //Filters.in("6", "attack", "security")
                                // /*
                                Filters.and(
                                        // Reports with ID = 10, 11 will be treated as security incidents
                                        Filters.in("0", 10, 11),
                                        // Count any security report with types "attack", "security" or "evasion"
                                        Filters.in("6", "attack", "security", "evasion"),
                                        Filters.lte("3", maxTstamp),
                                        Filters.gt("3", maxTstamp - 60000)
                                ) // -60000 since timestamps are in milliseconds
                                // */
                        ),
                        Aggregates.group("$4", Accumulators.sum("totalInstances", "$4"))
                )
        ).iterator();
        
        MMTMeasurement measurement = new MMTMeasurement();
        int foundIncidents = 0;
        // Iterate over the result, attaching the result to the measurement
        while(cursor.hasNext()){
            Document doc = cursor.next();
            measurement.addValue("property-" + doc.getString("_id"), doc.toJson());
            foundIncidents += doc.getInteger("totalInstances");
        }
        
        measurement.setValue(foundIncidents);
        
        return measurement;
    }

}
