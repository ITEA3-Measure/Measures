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

@objid ("e105f351-4d9e-4bb0-8d2b-ccc26b7ab5d9")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("3ed0e3da-b5ba-4189-af1c-00b4731d9c2d")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("317e7765-2bf5-42fa-8945-18ac867c7f24")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("f704f93f-6adf-4a34-84ce-3596e5f25810")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("787c9ebf-4024-4c40-a1e1-540e01a38d7a")
    private Hawk.Client client;

    @objid ("96d27ddb-a4c5-4f94-8d65-1f144a6fb990")
    private ThriftProtocol clientProtocol;

    @objid ("292cf623-1bbb-4a5d-b419-6137e9287da8")
    private String currentInstance;

    @objid ("104318e7-ede2-4321-a81f-fadfd1ff8f70")
    private String defaultNamespaces;

    @objid ("9705cd2b-9da9-4841-8bf3-c696ebec4d9a")
    private String serverUrl;

    @objid ("dc74c4d5-29e6-43e1-ab12-3cdb8ffd1f4e")
    private String instanceName;

    @objid ("7e25b006-4ad3-4052-8ad1-31f5b43b794d")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("43cbbbb4-68b4-4a2f-be25-d7bf205c4945")
    private String repository;

    @objid ("3042ab60-38ee-4986-b1eb-cb9750291913")
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

    @objid ("1358f342-5643-423b-9599-868c301e2032")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@PresentationLayer'.isSubstringOf(n.Content)).size();";
    }

    @objid ("093c0c8d-f68f-4ea0-9cec-6c7d30d5c9f5")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("895260e0-2ce6-4afa-948d-ca96c2cbcc17")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("a58a93f1-def6-41dd-abba-9b0592037fdb")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("263e04cb-6e67-46e8-a8e4-f8756428c751")
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

    @objid ("82d31c01-43b7-49d8-9446-0cba69a41ec5")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("76245c24-e2e9-4739-aa46-b70dfa9a1056")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("72e54d15-0acf-4d89-b003-e48c80c0b2b5")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
