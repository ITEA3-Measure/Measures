package org.measure.impl;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.apache.thrift.transport.TTransport;
import org.hawk.service.api.Hawk;
import org.hawk.service.api.HawkInstance;
import org.hawk.service.api.HawkQueryOptions;
import org.hawk.service.api.QueryResult;
import org.hawk.service.api.utils.APIUtils.ThriftProtocol;
import org.hawk.service.api.utils.APIUtils;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("422979ff-80d7-4a8b-a8b2-d12d7695da45")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("fb34fcd0-c897-46fa-be35-3fd0742a36bd")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("f0c0c54f-38f1-4c29-a1e1-04584ba6f31d")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("b99533af-5d2b-46b9-ad39-79ea09dec2f8")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("086ecc98-551c-409d-87ad-93b03421c150")
    private Hawk.Client client;

    @objid ("f52594f7-634c-4466-a382-359e96a594c3")
    private ThriftProtocol clientProtocol;

    @objid ("73656634-2529-400d-b8e0-9f77ae12c07f")
    private String currentInstance;

    @objid ("e237bc05-e376-422f-b63b-740f525447c3")
    private String defaultNamespaces;

    @objid ("8a8cfebb-6c1c-4046-9a3f-e29f7c3e5c9e")
    private String serverUrl;

    @objid ("674fe69e-42fd-4cfb-a0c5-813076c8bb06")
    private String instanceName;

    @objid ("862a6203-0836-48ff-9cff-0a56b9c9363a")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("00508aa1-353f-410d-8a56-0012630d76b0")
    private String repository;

    @objid ("33269608-5486-4a51-8578-b01d5ead6e75")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        try {
            // retrieve Properties
            initProperties();
        
            // check query is valid
            if (query == null || query.isEmpty()) {
                // throw exception
                throw new Exception("No Query is specified");
            }
        
            // connect to server if not connected
            connect(serverUrl, "", "");
        
            // select instance if not selected
            selectInstance(instanceName);
        
            
            try {
                   // send query
                QueryResult qResult = executeQuery();
                
                DoubleMeasurement res = new DoubleMeasurement();
                
                if(qResult.isSetVDouble()){
                       res.setValue(qResult.getVDouble());       
                }else if(qResult.isSetVInteger()){
                    res.setValue(new Double(qResult.getVInteger()));      
                }else if(qResult.isSetVLong()){
                    res.setValue(new Double(qResult.getVLong()));      
                }else if(qResult.isSetVShort()){
                    res.setValue(new Double(qResult.getVShort()));      
                }
                   
                result.add(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
         
            
            // disconnect
            disconnect();
        
        } catch (Throwable e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
        return result;
    }

    @objid ("b5380c3e-9775-41cc-8022-0d4330bd1ae5")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        //Number of Requirement        
        //Total number of Requirement defined in the selected scope
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query ="var cls =  Class.all;"
          + "var clsOfClass = cls.select(c|c.hawkParent.isTypeOf(Class));"
          + "var itrf = Interface.all;"
          + "var itrfOfClass = itrf.select(c|c.hawkParent.isTypeOf(Class));"
          + "if(cls.size + itrf.size > 0){"
          + "return (clsOfClass.size +  itrfOfClass.size) / (cls.size + itrf.size);"
          + "}"
          + "return 0.0;";
    }

    @objid ("a34d96bc-a0f0-4342-bdec-fcfac7ddf472")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("d2c8cb4e-934e-47a3-8b0c-9f1c6eaa61d1")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("6000f803-cb66-497d-809c-1056aa5fa1e6")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("f5d931c5-ee52-4ab5-96c9-f125c39460a9")
    protected QueryResult executeQuery() throws Exception {
        checkInstanceSelected();
        HawkQueryOptions opts = new HawkQueryOptions();
        opts.setDefaultNamespaces(this.defaultNamespaces);
        opts.setFilePatterns(new ArrayList<String>());
        opts.setRepositoryPattern(repository);
        opts.setIncludeAttributes(true);
        opts.setIncludeReferences(true);
        opts.setIncludeNodeIDs(false);
        opts.setIncludeContained(true);
        opts.setIncludeDerived(true);
        return client.query(currentInstance, query, "org.hawk.epsilon.emc.EOLQueryEngine", opts);
    }

    @objid ("792b7302-e3b8-4961-9f18-b7e9fd8ab172")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("fe99a594-0f3a-43c9-9b15-a73aeac7c4d8")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("fa1ff909-7f05-4c9c-8444-ec423efd83e3")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
