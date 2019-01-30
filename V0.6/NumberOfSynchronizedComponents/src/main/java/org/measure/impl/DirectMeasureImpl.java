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

@objid ("461d9b96-745f-4e06-9982-fdd60e30bc7f")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("7663e3d7-bb0d-4b80-8052-17350196f404")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("b00b72c1-feaf-4433-b987-312b1d8639a1")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("3443b4e2-0239-499c-be74-3da7f9e94819")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("9fb500cf-b70e-44e8-9983-29344b299a8a")
    private Hawk.Client client;

    @objid ("1af6ecf0-f42e-4444-9545-4e6699563677")
    private ThriftProtocol clientProtocol;

    @objid ("f11bc74d-f4b6-44db-bfa5-ec6c23f67b4c")
    private String currentInstance;

    @objid ("5912ba8e-e233-466a-8710-b8b8cfa9d26a")
    private String defaultNamespaces;

    @objid ("b29d452f-fcf6-4bfc-9fd0-4ee92af0eedf")
    private String serverUrl;

    @objid ("9d89147e-5719-415b-851a-2ebbe8bf4cc6")
    private String instanceName;

    @objid ("1cf01021-1b71-4e4b-a809-097b6538fd6c")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("4aeb3a64-60e7-4bda-b636-d522860d1882")
    private String repository;

    @objid ("9185fb4d-2351-4ec8-8060-15caa4969aa8")
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
                // Execute query
                QueryResult qResult = executeQuery();
        
                IntegerMeasurement res = new IntegerMeasurement();
                res.setValue(qResult.getVInteger());
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

    @objid ("72a9789e-012e-4b6f-9155-c99f524aa244")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Synchronized'.isSubstringOf(n.Content)).size();";
    }

    @objid ("302faf12-1261-4e49-9324-14f609fbf566")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("f0523929-ae65-44a7-8653-3cad8b318f84")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("173ee1dd-7c7e-477f-9d3e-eda0b5d1e4e5")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("12c1e982-3d14-468d-a8e7-a0ae37b2180e")
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

    @objid ("42ff121f-1df3-464b-b0e2-3dbfadcc8013")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("cecef7c6-c66c-47ed-9641-3879e2b52213")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("8362f713-9049-47ee-860e-ffb4cb08e6e6")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
