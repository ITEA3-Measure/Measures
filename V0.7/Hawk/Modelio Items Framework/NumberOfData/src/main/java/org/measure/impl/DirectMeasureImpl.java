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

@objid ("3cecca1a-6c1b-45f2-85d3-b2a9e07727a8")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("88be4675-8611-4df4-9373-56a504f1df4e")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("a9efba48-aa56-4a35-ab3a-4528ebd75cf4")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("5a5df67f-7915-473a-bbc7-3cf76c702bbf")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("9effa460-e685-4ee9-bb7a-97c69df90a24")
    private Hawk.Client client;

    @objid ("11257b12-47ed-4581-92f6-6ac3cef1a290")
    private ThriftProtocol clientProtocol;

    @objid ("865726c9-4e98-4cf5-8fca-742ee7b59417")
    private String currentInstance;

    @objid ("fcd1b203-cb73-4c50-88f2-5d6ac4d17228")
    private String defaultNamespaces;

    @objid ("5ab965af-b8d7-4047-87d8-fc285ffd85dd")
    private String serverUrl;

    @objid ("3f7b3f35-34e3-42cd-b4eb-b40150bbba69")
    private String instanceName;

    @objid ("c9d90f00-3691-4c91-b956-37f54d5123cc")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("b721df62-c946-4e7d-bf51-b575286172a4")
    private String repository;

    @objid ("3c37c0ea-7a34-4899-89fe-01d4b4faeef8")
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

    @objid ("9b4cae7b-5dbd-4071-8062-7b54e7fd28c4")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@Data'.isSubstringOf(n.Content)).size();";
    }

    @objid ("17750170-f5d9-4c1c-a3ae-ddede3798f92")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("de74d0e7-9895-49dd-b5cf-ad56254147ba")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("a2cf3bff-5d27-4b40-9bdb-e98404ffdb05")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("8a445797-3fb4-42de-803d-827ffb3e7cfe")
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

    @objid ("a8b82fa1-cdbf-4233-8d6a-ba56d814e63a")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("f4d5a498-f65c-4338-8ff1-28e4655128b3")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("887ee26b-d7ff-4654-806c-a43b0e029ebd")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
