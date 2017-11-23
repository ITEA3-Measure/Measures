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

@objid ("16f91ee6-7c44-46c1-9546-8639fae51b10")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("9b92516e-0547-4efd-b544-b8149a13d26d")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("3da46e53-846d-43bf-a60a-53af7d38fef9")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("a4fdcea8-a80c-4a28-b058-d69737930f99")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("9b8c102d-fac4-4d84-87bc-dacd6a5a4046")
    private Hawk.Client client;

    @objid ("438add80-b3f8-4ca6-ae12-673d6c2891a3")
    private ThriftProtocol clientProtocol;

    @objid ("c0c37865-5028-4ecb-8d88-d72cc09ef3ae")
    private String currentInstance;

    @objid ("9cc75b5d-2fed-4ff3-9893-6b6be3ab99bd")
    private String defaultNamespaces;

    @objid ("de88519f-db3c-4844-8b03-2ea9854a871e")
    private String serverUrl;

    @objid ("8479cd6c-17cb-4532-bb10-f76d429c7703")
    private String instanceName;

    @objid ("a4fe6aab-d619-4b63-a237-4a944d747d1a")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("8b13facd-b5df-44d8-8e54-4ceeb376eed3")
    private String repository;

    @objid ("c2327c60-43b5-4a5a-aead-89003e2eb3c8")
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

    @objid ("b260b329-4d04-4d2c-96a5-f0639f02d041")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.defaultNamespaces = "";
        this.query ="var cls =  Class.all;"
           + "var itrf = Interface.all;"
           + "if(cls.size + itrf.size > 0){"
           + "return (cls.select(c|c.IsAbstract).size + itrf.select(c|c.IsAbstract).size)  /( cls.size + itrf.size) * 100;"
           + "}"
           + "return 0;";
    }

    @objid ("d77909f1-f761-43c4-907f-cb1630448191")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("8bddc497-6ba2-4a35-a7fe-563d1e452814")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("9708a968-c743-4094-913d-3b0ff6384063")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("c6d183e3-47b8-49ba-8250-398ff7cb7a24")
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

    @objid ("802e4838-bce6-4a8a-a3e1-6fadabb8aec9")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("8f6d3fdb-4df9-466f-af87-241243bc8ecd")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("3b4cdf17-f499-46cf-9eff-27aa6b5aa752")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
