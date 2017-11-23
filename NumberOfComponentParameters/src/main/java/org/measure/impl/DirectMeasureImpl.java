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

@objid ("15152e10-48a5-4fb1-acd4-84d79929a820")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("13964d7f-7720-4d1d-abde-f2bd4234a54f")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("57c66be5-5f40-4236-a91d-362e73e8a5bf")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("83491a22-697c-42b2-b423-075074b768a7")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("64a764c1-6839-4a09-a8c4-2bc0224d7c46")
    private Hawk.Client client;

    @objid ("ae0528b9-e254-4ada-a340-9f1251948b2e")
    private ThriftProtocol clientProtocol;

    @objid ("15336221-e68b-4703-ba6b-e64da6d15328")
    private String currentInstance;

    @objid ("b92cca35-ae22-47d2-908f-ba810254976f")
    private String defaultNamespaces;

    @objid ("7d47897e-0b6e-47b4-af6b-82c7aaea8244")
    private String serverUrl;

    @objid ("f046bb3c-b8a5-4dde-b0a4-168439dbd727")
    private String instanceName;

    @objid ("6045f33d-113d-4cdf-8360-d9c3b4910a0a")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("0223e3bd-ec8a-4a5c-9c95-203ee5472789")
    private String repository;

    @objid ("360bc3c0-c578-4c9e-91d8-cc7694046e6b")
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

    @objid ("b62ab008-ede5-4f8a-bac8-0a57bc4231e7")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@ComponentParameters'.isSubstringOf(n.Content)).size();";
    }

    @objid ("10a76f2a-0886-4ba3-b7bd-4745e810d7b4")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("8067a482-0a67-4fad-8a8d-d0cb0744b730")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("1ae7ec3e-ecf5-4a99-bba4-2ea3625f1582")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("b49bf25c-e648-4da9-8094-e8703c321155")
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

    @objid ("93427161-ae8a-48cf-9c8e-680667e33497")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("76a7d028-fb6b-48dd-b070-4354e3839ea3")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("85290d18-1830-4f05-9777-8aaac347a4a7")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
