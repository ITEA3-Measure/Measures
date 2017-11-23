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

@objid ("2775a758-e1db-445a-8f73-a4e6f57da2c1")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("bba5f2a3-19de-4cab-9aeb-485aba78ea8d")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("2231df14-231c-4576-b673-1039b222fff3")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("84944272-4d1b-4937-a3d4-0365c0b3de86")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("1e479ef8-b363-4832-ba1b-25dccd775797")
    private Hawk.Client client;

    @objid ("ab3a6ff6-1939-47c9-9c35-63eeec04242c")
    private ThriftProtocol clientProtocol;

    @objid ("ec652b4f-d0d5-4344-842e-f2dfcc5abdcc")
    private String currentInstance;

    @objid ("0e794ca3-788e-4472-aed1-99c16e0366bf")
    private String defaultNamespaces;

    @objid ("6a7a8224-ff0a-4375-8c04-cd461e9d1147")
    private String serverUrl;

    @objid ("f73e0c21-9d89-48d2-a875-90d2b641ca36")
    private String instanceName;

    @objid ("5cf3e0a9-7434-4c50-b7d5-70548f4eb2a4")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("b906a84e-9d01-42c9-a6e6-d11177024925")
    private String repository;

    @objid ("770f35e5-1c7e-4821-bd9f-2e97b624c98e")
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

    @objid ("762253d1-e4ad-4f17-85a8-7e933131bb10")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Replicable'.isSubstringOf(n.Content)).size();";
    }

    @objid ("c1c0efb8-4ea4-4a73-8ca7-128845d61506")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("c27feeaa-e887-432c-ad43-f98ec14daa34")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("c5c04733-7785-492e-a857-7ddef4d9def5")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("0c6b4437-92da-4413-9712-5fc1648a665b")
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

    @objid ("064bd5d7-bea2-44f2-8ecf-2c8a70a83412")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("561bb6a5-ce1e-451d-8989-f79ff22bbaa8")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("e323eb81-0efc-4ba1-a546-1721efd8ce1c")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
