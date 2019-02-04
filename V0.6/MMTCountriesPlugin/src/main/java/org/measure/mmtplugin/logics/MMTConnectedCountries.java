package org.measure.mmtplugin.logics;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.measure.mmtplugin.MMTMeasurement;

public class MMTConnectedCountries extends MMTLogic{

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
            // Create the MMTMeasurement object to store the retrieved data
            MMTMeasurement measurement = new MMTMeasurement();
            Map<String, Integer> originCountries = new HashMap<>();
            Map<String, Integer> destinationCountries = new HashMap<>();
            
            // Aggregate the column #40, grouping by value only if required by the user
            if(this.information.get("includeOriginCountries").equals("true")){
                MongoCursor<Document> cursorb = collection.aggregate(
                        Arrays.asList(
                                Aggregates.match(
                                        Filters.and(
                                                Filters.eq("0", 100),
                                                Filters.lte("3", maxTstamp),
                                                Filters.gt("3", maxTstamp - 60000) // -60000 since timestamps are in milliseconds
                                        )
                                ),
                                Aggregates.group("$40", Accumulators.sum("originCountries", 1))
                        )
                ).iterator();

                while(cursorb.hasNext()){
                    // TODO - Insert every retrieved row in the MMTMeasurement object
                    Document doc = cursorb.next();
                    originCountries.put(doc.getString("_id"), doc.getInteger("originCountries"));
                }
                
                // Add the informaiton to the objecto to be returned
                measurement.addValue("totalOriginCountries", originCountries.keySet().size());
                measurement.addValue("originCountries", originCountries.keySet().toString());
                
                measurement.setValue(originCountries.keySet().size());
            }
            
            // Aggregate the column #41, grouping by value only if required by the user
            if(this.information.get("includeDestinationCountries").endsWith("true")){
                MongoCursor<Document> cursorb = collection.aggregate(
                        Arrays.asList(
                                Aggregates.match(
                                        Filters.and(
                                                Filters.eq("0", 100),
                                                Filters.lte("3", maxTstamp),
                                                Filters.gt("3", maxTstamp - 60000) // -60000 since timestamps are in milliseconds
                                        )
                                ),
                                Aggregates.group("$41", Accumulators.sum("destinationCountries", 1))
                        )
                ).iterator();

                while(cursorb.hasNext()){
                    // TODO - Insert every retrieved row in the MMTMeasurement object
                    Document doc = cursorb.next();
                    destinationCountries.put(doc.getString("_id"), doc.getInteger("destinationCountries"));
                }
                
                // Add the informaiton to the objecto to be returned
                measurement.addValue("totalDestinationCountries", destinationCountries.keySet().size());
                measurement.addValue("destinationcountries", destinationCountries.keySet().toString());
                
                measurement.setValue(destinationCountries.keySet().size());
            }
            
            return measurement;
    }

}