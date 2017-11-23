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
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("5a857803-674b-44ac-976f-4411c8098a9e")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("1a6e3b60-608b-49d3-b291-6ae1f9ac2318")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("5e47c4df-5a9a-42c0-8240-beb6f4856cf6")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("dabbde31-367f-4a6f-adff-9aacc47014ae")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("24d069fc-004d-4585-ac30-1c847892ba83")
    private Hawk.Client client;

    @objid ("63bee97f-f6f5-46a5-a732-be8c6e5fbf76")
    private ThriftProtocol clientProtocol;

    @objid ("ed337683-bed0-4e5f-b9b2-bee488f10b11")
    private String currentInstance;

    @objid ("061d5c08-528e-402f-b8d6-9db49faadc39")
    private String defaultNamespaces;

    @objid ("c42721dd-8ba1-4a26-bb83-c811caebdd87")
    private String serverUrl;

    @objid ("37a564f8-a79a-49a3-834e-1d8a60370cc9")
    private String instanceName;

    @objid ("be41602f-d945-4ea2-8e99-c3cc3a0660de")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("8e26548e-cf1e-426f-a725-0204c54b42ca")
    private String repository;

    @objid ("937c4901-85fe-4b6f-bcce-7ccb12002d16")
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
            
            IntegerMeasurement res = new IntegerMeasurement();
            res.setValue(qResult.getVInteger());        
            result.add(res);
            
            // disconnect
            disconnect();
        
        } catch (Throwable e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
        return result;
    }

    @objid ("9cd02a54-b378-4cf2-9323-0e98cbe98665")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Component.all.size();";
    }

    @objid ("6b55900b-1eb6-4a6d-a016-9faaad1e4eb7")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("3ceea9a2-eec6-4c3d-9e01-20701a1b4886")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("c7d146f9-3bff-4e1f-a78f-54805d82d90b")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("8fdce9c0-45cc-497e-8a38-945e052192e7")
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

    @objid ("940649c0-6675-4d60-9673-c5d7ad9e3f2b")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("14963178-db52-49c7-9b31-e523d1ab4f7e")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("568412c3-fd2d-4bde-92ff-6df4bf756291")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
