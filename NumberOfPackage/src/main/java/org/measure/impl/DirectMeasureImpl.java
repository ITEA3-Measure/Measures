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

@objid ("86afc6ac-ff0c-4dea-bfe4-ba57df2cecee")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("b2927779-720b-4388-9999-5e928ba85373")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("6b30164d-a910-4d05-a999-3102131b6558")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("d238b357-123d-425f-aa73-3107a20a0d80")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("750dddbf-82b1-448d-81a4-7b97951609d9")
    private Hawk.Client client;

    @objid ("5f6f8b39-298b-4849-968f-50a8a4030480")
    private ThriftProtocol clientProtocol;

    @objid ("ba71c497-a5ff-4174-aad9-34194f03a594")
    private String currentInstance;

    @objid ("5c08c88d-3220-4356-9284-46b4ba8ca124")
    private String defaultNamespaces;

    @objid ("b8606e1a-28f9-4ec8-8ad7-9c8a69845b8f")
    private String serverUrl;

    @objid ("2157fd41-f3fd-4213-a681-1d0a7338e26d")
    private String instanceName;

    @objid ("f73b68dd-c843-40ee-abe7-732d6ee845af")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("352bbc44-d625-41e6-9c8e-bf4548937837")
    private String repository;

    @objid ("ce5400d8-d427-40c2-983e-6278129f12cf")
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

    @objid ("ab8e0a69-77ef-4d2a-a393-931de7d807a2")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Package.all.size;";
    }

    @objid ("7c3746d7-2d92-47ff-a7d3-20e744273edd")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("d3edde28-e8cd-4169-9749-c29ad9be3ec7")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("88ac7466-e617-4d9f-98cb-75af32347e34")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("40d415d4-c91e-44f9-a0cb-45c5f5d37630")
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

    @objid ("5fd57a70-88c1-4a35-8f17-18aafd7e7657")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("dba526f3-3e80-450b-86de-e660c362f303")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("d6e2a3bb-fbaf-4958-a8d6-9b948db7d145")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
