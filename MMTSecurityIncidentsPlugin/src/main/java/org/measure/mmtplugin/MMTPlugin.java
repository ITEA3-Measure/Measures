package org.measure.mmtplugin;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.measure.mmtplugin.logics.MMTLogic;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class MMTPlugin extends DirectMeasure {
    
    // Constants used to identify the fields on the XML file
    private static final String MMT_DB_HOST_FIELDNAME = "mmtdbhostname";
    private static final String MMT_DB_PORT_FIELDNAME = "mmtdbportname";
    private static final String MMT_DB_USERNAME_FIELDNAME = "mmtdbusername";
    private static final String MMT_DB_PASSWORD_FIELDNAME = "mmtdbpassword";
    private static final String MMT_DB_DATA_FIELDNAME = "mmtdbattribute";
    
    // Constants related to the Database
    private static final String MMT_DB_NAME = "mmt-data";
    private static final String MMT_DB_DATA_COLLECTION_NAME = "data_detail";
    private static final String MMT_DB_SECURITY_COLLECTION_NAME = "security";
    
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        // Correctly configure the log level
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        
        // Extract the parameters from the XML configuration file
        String mmtDBHostname = this.getProperty(MMTPlugin.MMT_DB_HOST_FIELDNAME);
        int mmtDBPort = Integer.parseInt(this.getProperty(MMTPlugin.MMT_DB_PORT_FIELDNAME));

        // Create a new client connecting to the host and port specified in the XML file
        // Use these line to connect using a username and password
        /*
        String mongoUsername = this.getProperty(MMTPlugin.MMT_DB_USERNAME_FIELDNAME);
        String mongoPassword = this.getProperty(MMTPlugin.MMT_DB_PASSWORD_FIELDNAME);
        MongoCredential credentials = MongoCredential.createCredential(mongoUsername, MMTPlugin.MMT_DB_NAME, mongoPassword.toCharArray());
        // and get the database (will be created if not present)
        try(MongoClient client = new MongoClient(new ServerAddress(mmtDBHostname, mmtDBPort), Arrays.asList(credentials));){
            MongoDatabase db = client.getDatabase(MMTPlugin.MMT_DB_NAME);
        */
        // /*
        try(MongoClient client = new MongoClient(mmtDBHostname, mmtDBPort);){
            MongoDatabase db = client.getDatabase(MMTPlugin.MMT_DB_NAME);
            // */
            
            // Obtain the right logics instance
            // IMPORTANT!!! Change the value of the argument to compute other metrics
            MMTLogic.Metrics metrics = MMTLogic.Metrics.SECURITY_INCIDENTS;
            
            // Extract the collection from the database. This will depend on the required metrics
            MongoCollection collection;
            switch(metrics){
                default:
                    collection = db.getCollection(MMTPlugin.MMT_DB_DATA_COLLECTION_NAME);
                    break;
                case SECURITY_INCIDENTS:
                case ANOMALIES:
                    collection = db.getCollection(MMTPlugin.MMT_DB_SECURITY_COLLECTION_NAME);
            }
            
            MMTLogic logics = MMTLogic.getLogic(metrics);
            // Pass all the information to the logic Class
            this.getProperties().keySet().stream().forEach(key -> logics.addInformation(key, this.getProperty(key)));
            // Delegate the computation of the metrics and retrieve the result
            MMTMeasurement measurement = logics.computeMetrics(collection);
            
            // Add the Measurement object to the result list and return the list
            List<IMeasurement> results = new ArrayList<>();
            results.add(measurement);
            return results;
        } // try-with-resources will close the client automatically before returning // try-with-resources will close the client automatically before returning
    }
}
