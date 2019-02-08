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

@objid ("3d36dc89-9505-40bc-82ef-7f0cbf3fe14e")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("dd9b1ffe-f7bb-4173-9df5-d07271177bd6")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("1f2164af-6116-4ad3-b782-3f32da65b8db")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("4e96f9b6-e8d3-4f47-80ab-88445182c5fa")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("567b5890-7206-42eb-a640-b547067c9d9d")
    private Hawk.Client client;

    @objid ("2be5850b-d73b-4b97-b0d0-e77e403ea8d6")
    private ThriftProtocol clientProtocol;

    @objid ("7c903848-006e-4c41-858d-084d8e21de98")
    private String currentInstance;

    @objid ("8d8aecd7-894c-4445-9036-509da82f1ca9")
    private String defaultNamespaces;

    @objid ("140f17f3-f901-4d42-8bf1-8175f53b8828")
    private String serverUrl;

    @objid ("fba4b2cc-aaad-49aa-8beb-6fa92b14511e")
    private String instanceName;

    @objid ("09edb7ac-d55f-414d-9870-ccc2d172a9b0")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("5cb45047-c9fa-4037-b7bd-c49ef10c473f")
    private String repository;

    @objid ("ba6127f1-a086-4b47-944b-4ce8c3fd8e32")
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

    @objid ("4674de35-521a-45b9-9e98-d4269d388968")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@ComponentServices'.isSubstringOf(n.Content)).size();";
    }

    @objid ("bca87816-fbbb-469d-ae8a-2760db6d1d66")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("116ac98b-c2e9-4432-ac68-fd571003144e")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("e7834607-df81-4bdd-b2e5-7887a1a06154")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("ee74ec5f-ffe6-437e-ae7a-cd592c41bb76")
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

    @objid ("ec586165-a09a-49c3-899b-a40e04b3b7ad")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("317fab65-55f6-4640-aef7-a8fe5c0f8a6c")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("fce414d0-4f3b-4673-b527-2cfa75e49644")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
