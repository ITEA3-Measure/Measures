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

@objid ("d78fac68-eb7b-4452-a949-ae1892ac66d3")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("9254b93b-bbb5-4dbb-8615-585c3d312994")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("fc135589-25a7-4c76-839e-5f6063fd8965")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("f65374df-1d32-4a3a-b35c-b7dddd2ff00c")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("45df1649-5c1b-40bc-b188-2509e4683a6e")
    private Hawk.Client client;

    @objid ("b6e6fd2d-ad99-41ef-a5b3-fba855f03847")
    private ThriftProtocol clientProtocol;

    @objid ("a0808125-5208-4ac0-a6c7-2d63a4b7a708")
    private String currentInstance;

    @objid ("08306f7b-66f0-465b-b8ea-54c227b0fde3")
    private String defaultNamespaces;

    @objid ("a18f5694-a7f6-4598-91e8-05611439e5cb")
    private String serverUrl;

    @objid ("b06f9b5d-1552-4143-91ea-e713fa34c0eb")
    private String instanceName;

    @objid ("dec8e456-60f5-4b25-a47d-591646b2d4ba")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("fa3bbcb7-24d4-4001-a293-7a58a9fdb2dd")
    private String repository;

    @objid ("6e901854-e050-42d4-b9c3-5d1fbc8bc335")
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

    @objid ("0d6e2b62-58c0-42f4-8f70-2dbdbd686a42")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@ComponentImpl'.isSubstringOf(n.Content)).size();";
    }

    @objid ("4105640d-3334-4e49-8c03-addcd3f9f018")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("d2608b3a-d9ab-43df-90cd-0422611cd43d")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("55d09522-0102-40b2-a129-8db300483a1d")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("72ecda2c-f599-4960-9baf-444397b625e2")
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

    @objid ("84393e89-385f-4228-8dc2-72992ab7b86a")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("ff50fbc8-c2a6-48a8-835c-52d90d158fe9")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("ae3589a6-7d26-4b69-9793-2ddb580e2c73")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
