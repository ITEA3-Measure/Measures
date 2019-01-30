package org.measure.mmtplugin.logics;

import com.mongodb.client.MongoCollection;
import java.util.HashMap;
import java.util.Map;
import org.measure.mmtplugin.MMTMeasurement;

public abstract class MMTLogic {
    // FIXME - Extend this enum and create the supporting classes to
    // support new metrics
    public enum Metrics{
        DL_UP_BANDWIDTH,
        DL_BANDWIDTH,
        UP_BANDWIDTH,
        TX_PKGS,
        PARALLEL_SESSIONS,
        CONNECTED_COUNTRIES,
        APPLICATION_RESPONSE_TIME,
        NETWORK_RESPONSE_TIME,
        CONNECTED_DEVICES,
        UNIQUE_PROTOCOLS,
        UNIQUE_USERS,
        SECURITY_INCIDENTS,
        ANOMALIES,
    }
    
    public Map<String, String> information = new HashMap<>();
    
    /**
     * Point of entry of the MMT logics. This factory will create an instance of MMTLogic
     * object capable of computing the given object.
     * @param metric The MMTLogics.Metrics value representing the metrics to be computed
     * @return An MMTLogic object that computes the requested metric.
     * @throws Exception In case of an error, a suitable exception is thrown.
     */
    public static MMTLogic getLogic(MMTLogic.Metrics metric) throws Exception{
        switch(metric){
            case CONNECTED_DEVICES:
                return new MMTConnectedDevices();
            default:
                throw new UnsupportedOperationException("This metrics is not supported");
        }
    }
    
    /**
     * The computeMetrics method is in charge of computing the value of the given metric.
     * Its implementation in subclasses varies depending on the different types of metrics computed.
     * @param collection The MongoDB collection that will be used to compute the value.
     * @return An MMTMeasurement object containing the given value. This object contains a mapping with
     * one or many names associated with the values computed.
     */
    public abstract MMTMeasurement computeMetrics(MongoCollection collection);
    
    /**
     * Add information to be used when computing the metrics. This method uses a map to
     * identify the information.
     * @param key The key to which the value will be associated.
     * @param value The value to store
     */
    public void addInformation(String key, String value){
        this.information.put(key, value);
    }
}
