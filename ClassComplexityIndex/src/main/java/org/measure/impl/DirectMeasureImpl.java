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

@objid ("909f52d1-5255-4e34-ac3d-f3a4507fa5a2")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("9cb774c0-cd20-4866-ae31-01bb4459d996")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("3e02a7db-ed91-4e19-987b-7da2851df7f5")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("5c550677-778e-42b4-b9df-6f5508d9dcd6")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("d9ffbb30-9095-411a-88a0-148f664dae4b")
    private Hawk.Client client;

    @objid ("a9a9a624-d4dd-4cc3-9644-517973c73298")
    private ThriftProtocol clientProtocol;

    @objid ("ef8c0f24-3387-48e7-b05d-3ebf369262ee")
    private String currentInstance;

    @objid ("da6ec33a-13ae-4465-80c3-71a019885a5a")
    private String defaultNamespaces;

    @objid ("7efa8aa3-67bd-4689-bc6f-1a1560d0016c")
    private String serverUrl;

    @objid ("9a5ff70e-4b9e-4591-af7b-54c3f2e28be7")
    private String instanceName;

    @objid ("13c60c49-95a6-4a55-bca7-7c8a30583a51")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("245750a9-cfab-44ae-9640-12409149121c")
    private String repository;

    @objid ("9988e168-9488-441d-9c87-604694f72760")
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
            
            // disconnect
            disconnect();
        
        } catch (Throwable e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
        return result;
    }

    @objid ("72688eb0-3454-498b-9ea1-9ab783df8b42")
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

    @objid ("0a27b306-7c78-4019-8fd1-5b792f2ecf6f")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("60544c51-0c83-4990-9977-4d1219d700d6")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("92953bda-2104-4ae6-8905-686a56b51c9a")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("92a67260-1de4-46a9-8d7a-69b243076a3f")
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

    @objid ("96b89047-c9b8-44ae-9509-5f7c4b122f66")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("8c0cf6c3-d249-427c-a9fb-3bcf5c7b77ce")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("5ad09e2b-f477-4cb9-8534-a55353d874b2")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
