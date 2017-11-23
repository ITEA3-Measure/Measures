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

@objid ("dece269c-595f-49ab-b0cb-b18198a01913")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("2f854288-164f-45dd-88d3-75b033a83285")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("6a74686b-a9fe-402f-8c6b-a930906daba0")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("e5f5e2ca-3efd-4784-9dd3-57a76af0f169")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("956b4e49-0bf3-4064-81a0-006122ae8bc9")
    private Hawk.Client client;

    @objid ("f7f72195-b2f7-41f1-aea5-345645d16724")
    private ThriftProtocol clientProtocol;

    @objid ("2a849353-ac43-48ab-8a61-74820ab7734f")
    private String currentInstance;

    @objid ("65fcd48b-f59c-4d4d-888f-a8831c0b9dda")
    private String defaultNamespaces;

    @objid ("4386342f-8053-480c-a3bf-fd7f44e57ae1")
    private String serverUrl;

    @objid ("446d9b1b-fdc4-41d4-82f0-575e0469d96f")
    private String instanceName;

    @objid ("f0dee477-d412-4011-a0c1-4f43d1ad2b86")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("521861de-7232-474b-a54c-460008e136ac")
    private String repository;

    @objid ("e0cc8e23-965e-4e57-98ff-e353f177a56b")
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

    @objid ("abce2988-3815-44d2-a09a-dba95e3d31fd")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Component.all.size;";
    }

    @objid ("68ead4f0-bca3-4d67-acab-fca39574b96c")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("d7982cda-ec72-4f04-b211-37e2071f10b6")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("418d97a8-d2fe-4f29-ae9c-f06fbd4a4a4a")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("a94551f8-cbdc-4747-84ab-4b3fedca52c6")
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

    @objid ("cbed2e5c-04fd-4b81-b7a6-6d9774ededde")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("f8b5d3ba-00c2-428b-bb02-40b3ce815dd6")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("38de985f-a0ce-40d6-baa6-90d4af6317a0")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
