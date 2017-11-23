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

@objid ("9318e4d2-4742-4fb1-8825-1fd946a6812a")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("395ec76d-676e-4db9-8374-f63d32ebb8c4")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("023bdf9a-fff4-40a8-8893-7c02015e625e")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("08802f4a-d927-46b1-b961-fb757253928c")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("328ca0b4-9a1c-4898-a3e1-c3a405710f9b")
    private Hawk.Client client;

    @objid ("e6d6502f-4c09-4b45-943d-07b240fa8c77")
    private ThriftProtocol clientProtocol;

    @objid ("5ce5b0d5-39dc-4cce-812b-ee51fec77579")
    private String currentInstance;

    @objid ("c0ba7446-666c-4035-a709-330041719a92")
    private String defaultNamespaces;

    @objid ("d929cd5c-8d22-4ff5-a2ce-39b49fba8282")
    private String serverUrl;

    @objid ("b98e5d71-7cd7-47b0-9514-2d6d9aa69494")
    private String instanceName;

    @objid ("f03e8cb1-53f3-4da0-be99-8fbd44185640")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("f7f26b35-e57e-429d-80cf-190e4be47eb7")
    private String repository;

    @objid ("49dbb615-b0c8-454b-8f3b-eeaf285dea54")
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

    @objid ("eed06052-49a3-43f2-b77f-7a2d5fc2b81a")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@Entity'.isSubstringOf(n.Content)).size();";
    }

    @objid ("c1934a8b-cf53-47b7-8d39-f26a3506c428")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("6c0fa06b-3bfe-4301-9ddd-7d05050f45b3")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("20648d0b-bf49-426c-ba14-67b4eb006e24")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("73433c06-2153-4b11-ae98-e38e7b390235")
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

    @objid ("27bdf08b-0005-4db3-817a-6d2bb6f6f967")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("88ac102d-97aa-4668-bd6d-d3e0a87fc1a9")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("4e800858-9026-4b8b-a142-c0076559db09")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
