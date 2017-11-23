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

@objid ("8d38500c-d507-45ae-a243-f7e003e3283e")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("5f0da345-ad59-4487-b633-242bd74a52c8")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("d277f8a0-c88e-45d1-b17d-0c63cfa6d5f9")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("fe8c021e-0c67-4eb2-a907-965cf906dac0")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("94f78192-0821-4a18-81f7-f01fcc4aae18")
    private Hawk.Client client;

    @objid ("0e2840ae-461e-4fc6-8384-af63edb7939d")
    private ThriftProtocol clientProtocol;

    @objid ("9945164b-1671-4d58-aeb7-069c27f4da4d")
    private String currentInstance;

    @objid ("d96b1f3b-f677-436a-ba55-4c7c0ed2924a")
    private String defaultNamespaces;

    @objid ("9326df35-73d8-44c9-9b9f-df8322b51a7a")
    private String serverUrl;

    @objid ("b86f72fc-cc35-4879-a10c-a17c808db30b")
    private String instanceName;

    @objid ("a2aa42f3-4094-4444-97f7-63c19e4a194c")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("00dcc9d7-b4ff-449d-97ad-09a067d20ffb")
    private String repository;

    @objid ("b29f03d8-8a73-482f-bf97-bef2530f333a")
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

    @objid ("dbad97d1-cfa6-4505-bc36-57e52a6e3136")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Class.all.size;";
    }

    @objid ("1558706b-5473-4675-821b-928d5624998a")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("8c2fdb3f-c787-4945-8b46-003c87eb3630")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("1d702560-9e0e-4c82-b997-1ace29398834")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("cbae709d-cd98-47b4-8b96-9c4e274d90db")
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

    @objid ("4ac3b46d-413b-42df-a677-64b23e5b0633")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("0dcc986c-e4f5-46f2-89b8-f15699782c16")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("ce369b09-0c41-43ee-b1dc-5434fe934d22")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
