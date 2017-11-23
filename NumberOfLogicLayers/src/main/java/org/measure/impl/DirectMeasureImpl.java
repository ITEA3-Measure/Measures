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

@objid ("83d736ef-fa38-406e-ba67-5ae5a92116d8")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("7a02abf7-f504-437b-9fb0-35c8d470922e")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("80129cb5-c667-4064-a278-aedbd18ba353")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("28962107-9a2a-4bd4-91e8-ad1e86bef39a")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("5e43f73b-27f4-4406-8746-288a76d71e7c")
    private Hawk.Client client;

    @objid ("6bc1dbd9-1d62-454a-9680-43b63ac7fa15")
    private ThriftProtocol clientProtocol;

    @objid ("dbb33d46-b924-439a-9c46-e6c0c7a0d132")
    private String currentInstance;

    @objid ("86a21987-c700-419d-8ba9-da79cf64a88a")
    private String defaultNamespaces;

    @objid ("ee82daf0-6d66-4b0a-b0a1-59505b614750")
    private String serverUrl;

    @objid ("0b455538-78bd-4770-b787-9e29921c6fd3")
    private String instanceName;

    @objid ("7b7a3549-b3ae-4ce4-b596-92f2f6c9eb59")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("ceb32105-c77f-4ea8-aa40-0ba4086677ec")
    private String repository;

    @objid ("1dec8e77-d361-4e63-b53b-bedcc45a4da8")
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

    @objid ("107bcb3d-ed0a-4cde-96ce-bc30bd26c29d")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@LogicLayer'.isSubstringOf(n.Content)).size();";
    }

    @objid ("6d98edf3-73c0-4c0a-baf1-c2fc6b4ed8ec")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("0c5122a2-1414-4c61-a35b-db05dbf2f8f5")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("89691fbd-8795-4bc1-97a8-29a5e299b90b")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("38df6c98-ccda-4f05-8f27-1a34cfaa5318")
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

    @objid ("f85d1618-8b02-484e-b284-965a0c80be91")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("1475f31d-f907-40c6-a72f-949fa9733ef2")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("95d20d7e-cd4d-4eeb-b549-0045cc5ba5d7")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
