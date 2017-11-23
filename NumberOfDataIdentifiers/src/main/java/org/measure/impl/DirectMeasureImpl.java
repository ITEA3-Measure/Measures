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

@objid ("581f478c-e2d6-43aa-8e69-5f05fa145434")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("90f758f7-e69d-422a-8af3-c8f2b80b3cf2")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("3ecabae2-5736-4f27-8132-58af3f796058")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("5d345ec8-3b49-4fb6-83f8-7248fa539ed4")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("57e7eefc-246e-418b-964e-e937405387c4")
    private Hawk.Client client;

    @objid ("fb0d6e99-47bb-4bd2-8b7a-49d72e5c137c")
    private ThriftProtocol clientProtocol;

    @objid ("dffeee3e-f076-487a-a596-c48e070883d7")
    private String currentInstance;

    @objid ("d0492991-2a44-408c-b155-4d2d21f40428")
    private String defaultNamespaces;

    @objid ("e4d749f0-7fd9-4ad3-82e6-b30da918cc94")
    private String serverUrl;

    @objid ("aa15ccf8-5586-4199-ac32-6f38652ea2b7")
    private String instanceName;

    @objid ("b206ded3-0266-4494-ba8e-f6c93e4c518c")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("42c595c8-aaec-4c2f-a738-9393842adba3")
    private String repository;

    @objid ("3092a7c3-75f8-4eac-9cf9-fb33a2112d47")
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

    @objid ("f38af69f-5cf0-43f3-8839-41902638baa8")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@DataId'.isSubstringOf(n.Content)).size();";
    }

    @objid ("5262859e-39f0-4a6e-a2c4-af77792727c3")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("44c01644-222c-4813-a3a4-55429841a49e")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("15738b2d-47f4-4443-b1c0-d81e65a866a5")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("a610029a-3109-44b3-a350-d49da1273478")
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

    @objid ("c8285928-bc27-4811-9da0-26aabb9a061a")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("17c87843-fee6-4f41-b0eb-0cf06fa8a0a9")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("49b8c1b8-86a9-49c6-bf35-1201240311b2")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
