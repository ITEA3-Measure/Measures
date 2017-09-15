package org.measure.mmtplugin.logics;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import java.util.Arrays;
import org.bson.Document;
import org.measure.mmtplugin.MMTMeasurement;

class MMTBandwidth extends MMTLogic {

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
            //long actualTime =  1504098147181;
            double maxTstamp = actualTime - (actualTime % 10000);
            // Aggregate the column #11 (DL_DATA_VOLUME) or #12(DL_PAYLOAD_VOLUME)
            MongoCursor<Document> cursorb = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(
                                    Filters.and(
                                            Filters.lte("3", maxTstamp),
                                            Filters.gt("3", maxTstamp - 60000)// -60000 since timestamps are in milliseconds
                                    )
                            ), 
                            Aggregates.group("$0", Accumulators.sum("totULBandwidth", "$11")) // THIS INCLUDES HEADERS!!! Change to 12 to avoid them
                    )
            ).iterator();
            
            // Create the MMTMeasurement object to store the retrieved data
            MMTMeasurement measurement = new MMTMeasurement();
            int upData = 0;
            
            while(cursorb.hasNext()){
                // TODO - Insert every retrieved row in the MMTMeasurement object
                Document doc = cursorb.next();
                // We will ignore statistics reports from MMT
                if(doc.getInteger("_id") == 99)
                    continue;
                upData += doc.getInteger("totULBandwidth");
            }
            
            // Aggregate the column #14 (DL_DATA_VOLUME) or #15(DL_PAYLOAD_VOLUME)
            MongoCursor<Document> cursora = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(
                                    Filters.and(
                                            Filters.lte("3", maxTstamp),
                                            Filters.gt("3", maxTstamp - 60000)// -60000 since timestamps are in milliseconds
                                    )
                            ),
                            Aggregates.group("$0", Accumulators.sum("totDLBandwidth", "$14")) // THIS INCLUDES HEADERS!!! Change to 15 to avoid them
                    )
            ).iterator();
            
            int downData = 0;
            
            while(cursora.hasNext()){
                // TODO - Insert every retrieved row in the MMTMeasurement object
                Document doc = cursora.next();
                // We will ignore statistics reports from MMT
                if(doc.getInteger("_id") == 99)
                    continue;
                downData += doc.getInteger("totDLBandwidth");
            }
            
            measurement.addValue("totalUpData", upData);
            measurement.addValue("upBW-Bps", (long) (upData / 60));
            measurement.addValue("upBW-KBps", (long) ((upData / 1024) / 60));
            measurement.addValue("upBW-MBps", (long) ((upData / (1024 * 1024)) / 60));
            measurement.addValue("upBW-GBps", (long) ((upData / (1024 * 1024 * 1024)) / 60));
            
            measurement.addValue("totalDownData", downData);
            measurement.addValue("downBW-Bps", (long) (downData / 60));
            measurement.addValue("downBW-KBps", (long) ((downData / 1024) / 60));
            measurement.addValue("downBW-MBps", (long) ((downData / (1024 * 1024)) / 60));
            measurement.addValue("downBW-GBps", (long) ((downData / (1024 * 1024 * 1024)) / 60));
            
            // IMPORTANT: For the moment, the plotted data will be the total bytes DOWNLOADED in the last 60 seconds
            measurement.setValue(downData);
            
            return measurement;
    }
}
