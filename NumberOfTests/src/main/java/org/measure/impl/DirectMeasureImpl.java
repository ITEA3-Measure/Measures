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

@objid ("3feb72d6-063d-41f1-9e99-3c57ba4cf2bd")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("bff18715-98b8-42a2-b539-a212bb97daf0")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("44fe6a92-6499-4a67-b113-0ee4d360178d")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("eef9c190-61ff-41a6-8e3b-2ef903d8193d")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("c790267e-50dc-43b9-b75e-00570fbcd772")
    private Hawk.Client client;

    @objid ("ed814db1-c502-4f7e-a288-511ce4a5cd37")
    private ThriftProtocol clientProtocol;

    @objid ("0b97aae7-b03c-4a12-a322-1dc65faf5d1d")
    private String currentInstance;

    @objid ("d152bee7-8cd9-4726-9ae1-5ce9619d6236")
    private String defaultNamespaces;

    @objid ("19b5b02c-a64a-4252-ae3f-e0b300f9b165")
    private String serverUrl;

    @objid ("50149c20-fc8b-4984-81fd-fe482c89058a")
    private String instanceName;

    @objid ("f579df71-0060-4bdb-9805-b440f0d294d2")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("ec738a97-ede5-41ee-8184-afa7a2ab0a41")
    private String repository;

    @objid ("de8c5318-5b26-4c70-8b0d-8f41d0b91c24")
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

    @objid ("0fbe7bd5-6824-423a-a4cd-8fa7a2c82760")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Test.all.size;";
    }

    @objid ("b2d62f44-3a00-4970-ad23-74341ad0f5b2")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("f5bfc1fe-3d70-47ce-958d-9292f97ed83d")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("c29b6498-e182-4a2a-a58e-9dd92a88def7")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("8c1baefa-29c5-45db-8f31-7b279791925c")
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

    @objid ("d138b857-e538-4f1a-aa37-2c76bcf7fa38")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("0cd5d34c-3d43-4ed9-a32e-9776f7f08359")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("0aa7ca2a-bfed-4529-82ac-5402487a5f4e")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
