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

@objid ("cd29d839-7729-4e2f-85e3-31bb51d60b82")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("49879504-760c-4bd6-a2df-5c47ad967c80")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("a1c2eb7a-8c6e-4441-926c-35fb3a91eff2")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("ba513bee-6d19-461e-bcc6-3d8d6ba5fd5c")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("31f0e2fb-da3c-47fe-aaf4-b9f46705f6d2")
    private Hawk.Client client;

    @objid ("8f8799d8-d6a3-4c1a-b577-f1310e126941")
    private ThriftProtocol clientProtocol;

    @objid ("bdf1700a-6d58-469d-9582-986aad5c758d")
    private String currentInstance;

    @objid ("1ad00f0b-6e29-4077-8742-cb37af709ba4")
    private String defaultNamespaces;

    @objid ("4e3a6cc3-b808-4d07-ae59-f66db7237d91")
    private String serverUrl;

    @objid ("e60505de-0358-45d7-a01c-162844af5644")
    private String instanceName;

    @objid ("4e61c5b3-c95f-4d7f-852b-fa284710c7ba")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("627fad46-3baa-41be-9cee-579bb3098c63")
    private String repository;

    @objid ("76176556-03da-4e52-a106-a317afdc0512")
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

    @objid ("1234ed9c-66b6-4899-93ab-94cb8a30a5c1")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Package.all.size;";
    }

    @objid ("c39fffd1-29f1-4763-9c7b-10d359287698")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("bba1c400-e658-40ee-bc26-92bf17c33385")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("64c9a687-fc67-4dc5-b413-a4cbd04e5f27")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("fb363785-50b9-44cd-8dae-d51c868099fb")
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

    @objid ("df71fded-7c96-4411-b963-34736b27a2ca")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("e9b285eb-474d-4902-b37c-ca43cff22c17")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("9abd9d84-cd63-4c6f-b9b3-0610820f077c")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
