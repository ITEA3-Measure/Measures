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

@objid ("a913d01f-53ea-4d11-a561-33cbbe83f9b3")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("a194a042-1cf3-421a-9f13-b9c4e24c5beb")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("24452009-1580-41cb-b5a3-31cb6f4cdc8e")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("21304405-69bc-4cba-a51e-cf4c36aa33bf")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("06f1aa1e-50a4-4455-8ebd-727b5d78a057")
    private Hawk.Client client;

    @objid ("d883ddc6-6683-4261-be6c-75e654f6b93e")
    private ThriftProtocol clientProtocol;

    @objid ("915d0bd6-c3e9-4daa-96c1-a056b5a5ce29")
    private String currentInstance;

    @objid ("953ad004-ceb0-47d4-987c-245dc5527060")
    private String defaultNamespaces;

    @objid ("2b0b932b-d7ac-4ece-b692-0c9cb6d6003b")
    private String serverUrl;

    @objid ("2f1e0aa0-1a9e-4f4c-8657-8bc6be435f6b")
    private String instanceName;

    @objid ("a1884470-a75f-48c0-8ca9-4af780306f48")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("cc3ee358-21f0-42e4-97d0-4754a8306809")
    private String repository;

    @objid ("3642e9b7-a837-44da-a3bd-0a21edba3c11")
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

    @objid ("d8a5da6f-ac51-4a5b-be3e-cb17a0586e83")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Interface.all.size;";
    }

    @objid ("8908def1-3877-40ba-b96f-4dda9a5be35b")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("4ca84b04-8a13-4513-8e98-1b5daa54d699")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("2b4e01c9-b943-419f-b4d8-65f642a5f7f8")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("bc4b0db6-0e87-43ef-93b4-736e9aad041b")
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

    @objid ("baa8f877-825f-4acd-828b-8d1bf722fee0")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("cb1d53d6-6185-4634-be0c-8b68f01adab7")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("d2f54fe2-15a4-400a-ab5f-280ec071dff7")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
