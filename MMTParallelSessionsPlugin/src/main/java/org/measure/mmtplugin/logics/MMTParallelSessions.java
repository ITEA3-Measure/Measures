package org.measure.mmtplugin.logics;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import java.util.Arrays;
import org.bson.Document;
import org.measure.mmtplugin.MMTMeasurement;

public class MMTParallelSessions extends MMTLogic {

    @Override
    public MMTMeasurement computeMetrics(MongoCollection collection) {
            // Here there are two methods of computing the "last minute":
            // The first one: Get the last registered timestamp and rest 60.000 ms
            //retrieve the last timestamp
            /*
            double maxTstamp = -1.0;
            MongoCursor<Document> cursora = collection.aggregate(
                    Arrays.asList(
                            Aggregates.group("$0", Accumulators.max("maxTstamp", "$3"))
                    )
            ).iterator();
            while(cursora.hasNext()){
                Document doc = cursora.next();
                //System.out.println(doc.toString());
                // We will ignore statistics reports from MMT
                if(doc.getInteger("_id") == 99)
                    continue;
                maxTstamp = doc.getDouble("maxTstamp");
            }
            */
            // The second one: Get the timestamp of the last COMPLETED minute and rest 60.000 ms
            long actualTime =  System.currentTimeMillis();
            //long actualTime = Long.parseLong("1504098147181");
            double maxTstamp = actualTime - (actualTime % 10000);
            // Aggregate the column #14 (DL_DATA_VOLUME) or #15(DL_PAYLOAD_VOLUME)
            MongoCursor<Document> cursorb = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(
                                    Filters.and(
                                            Filters.eq("0", 100),
                                            Filters.lte("3", maxTstamp),
                                            Filters.gt("3", maxTstamp - 60000) // -60000 since timestamps are in milliseconds
                                    )
                            ),
                            Aggregates.group("$0", Accumulators.sum("totalParallel", 1))
                    )
            ).iterator();
            
            // Create the MMTMeasurement object to store the retrieved data
            MMTMeasurement measurement = new MMTMeasurement();
            int parallelData = 0;
            
            while(cursorb.hasNext()){
                // TODO - Insert every retrieved row in the MMTMeasurement object
                Document doc = cursorb.next();
                parallelData = doc.getInteger("totalParallel");
            }
            
            //measurement.addValue("totalParallelSessions", parallelData);
            // This return value is for graphic purposes. Points to plot MUST be associated to the key "value"
            measurement.setValue(parallelData);
            
            return measurement;
    }
}
