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

@objid ("863778fe-bdf7-4c33-b75d-58b99d62b002")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("85b6319e-a6ca-42a7-a598-590ff817aa5e")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("fc03dd9b-177e-4479-8be4-4ae586222d1e")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("9840588d-1570-49ef-ac61-9370f8209fdf")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("733f0a84-78f1-41f9-9734-0ea33d3b628e")
    private Hawk.Client client;

    @objid ("26d2f2aa-4f1b-4c3d-804c-01ba328a9281")
    private ThriftProtocol clientProtocol;

    @objid ("514fdaea-0dc9-4e07-b5c9-1d885a7ab8de")
    private String currentInstance;

    @objid ("d1663b1b-5237-4662-8216-3587243cec5b")
    private String defaultNamespaces;

    @objid ("2d4d2155-bc51-456a-b954-739b5efb5577")
    private String serverUrl;

    @objid ("a18d6662-444d-4139-b404-57717e5e0be0")
    private String instanceName;

    @objid ("7925b382-f70d-447a-aa15-d5b39a5ea0b0")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("b7fa9a76-4912-4224-8d24-1efe4dea10f2")
    private String repository;

    @objid ("3808bb36-665c-4c1f-8db5-bbf19c16ca9d")
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

    @objid ("9d9292c8-e6af-43d4-8611-fb8893cecfc0")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@ComponentServices'.isSubstringOf(n.Content)).size();";
    }

    @objid ("1752b566-002c-48ab-9e5f-d4618212871a")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("e8443dbe-4485-43a1-9a02-8ce12fae10c8")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("9b7e92da-155d-4d8f-9b36-8672c223efa4")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("331a877f-42b7-4b86-aa6f-401956b7b74a")
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

    @objid ("6b26c0ab-24cf-4b01-bd4c-fc38194ff4f6")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("680698c3-2bc4-4047-afcf-bb7db3569b47")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("eb1129b5-43aa-43ec-a3b1-be7ad0e633f9")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
