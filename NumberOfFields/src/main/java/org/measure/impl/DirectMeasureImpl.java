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

@objid ("39e54da6-2293-4da7-82f1-11e8725c65ad")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("7b560630-b5b4-4665-99a3-51a0e6d3131c")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("4dba6dac-96eb-437c-b17b-52efc8365b2d")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("ed5ebced-223d-453e-8269-083655157311")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("a84184b3-97f3-436f-a032-a93c956c4d6e")
    private Hawk.Client client;

    @objid ("729a2293-15a9-4746-827f-50ece781aaa3")
    private ThriftProtocol clientProtocol;

    @objid ("8aea112c-b07d-49d2-b8f7-bd07d8d6cf06")
    private String currentInstance;

    @objid ("6235f57d-b06f-423b-8a24-88c2a9cdeada")
    private String defaultNamespaces;

    @objid ("32b5f234-6baa-45f5-9f3d-0b5022b7ae6f")
    private String serverUrl;

    @objid ("e2166491-b78d-4239-8626-eeb730eb932f")
    private String instanceName;

    @objid ("ee6917f9-356e-490d-bf5a-44ae2432df78")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("d04cc6f4-18d3-41da-ba72-67d374113ce3")
    private String repository;

    @objid ("b0e31c43-14b1-44b5-8e5b-3ed714bed462")
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

    @objid ("1257f143-52f4-4a5b-a062-60b8b5279337")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Attribute.all.size;";
    }

    @objid ("875314f9-38c0-4ea5-97f3-465a81c3eb54")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("4bdd840f-9981-4cac-a1e7-c060c84aad98")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("ba07811a-021f-483b-9ee2-4ef43e87daaa")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("61a06f9f-9309-43ec-98bd-9388bfea7e8e")
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

    @objid ("b0e8f632-c1db-4185-bdc0-c59ade60d861")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("5a17bca8-6a0b-4dad-8095-0ab1119ac289")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("2f5e9aab-cb39-4ce1-96b3-e1686d3938c7")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
