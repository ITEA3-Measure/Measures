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

@objid ("08991fa2-26a2-4d9d-a746-3c170225ec4b")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("84b274a9-ea41-4224-8991-2a64e2cce0ca")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("09583d7c-8815-4713-8b73-ac47cff4a618")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("0aa4ea31-3d3d-41d7-95ee-acf73c1417b3")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("d07b2a1d-80cd-4dcd-bacd-ebfa47525b07")
    private Hawk.Client client;

    @objid ("ad1c3101-3891-427c-a59b-d06748aae5f3")
    private ThriftProtocol clientProtocol;

    @objid ("c7525933-e9ae-48be-aa8e-3ddcb37eb3da")
    private String currentInstance;

    @objid ("6acc9504-a715-47ae-af1e-4eb343560020")
    private String defaultNamespaces;

    @objid ("5ff5768e-8c47-4f48-bdb4-00f14969b4e2")
    private String serverUrl;

    @objid ("bbc3643b-5487-43b8-be59-0879e9a30a5e")
    private String instanceName;

    @objid ("62bcd854-8994-4960-83e6-77f93f8d0586")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("a13e25b7-5b85-48f4-b00f-0a56cfb83318")
    private String repository;

    @objid ("357f26ec-4225-47b6-8ff1-e04077659abf")
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
        
            try {
                // Execute query
                QueryResult qResult = executeQuery();
        
                IntegerMeasurement res = new IntegerMeasurement();
                res.setValue(qResult.getVInteger());
                result.add(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // disconnect
            disconnect();
        
        } catch (Throwable e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
        return result;
    }

    @objid ("96db993c-a80d-4400-aa91-6e4dd12886cc")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Failoverable'.isSubstringOf(n.Content)).size();";
    }

    @objid ("b84fafc0-18c7-4a97-ba26-6781540d90ac")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("29ac742c-b426-498d-b865-5ba4acaa2053")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("3de5ced8-ea8f-42d4-8bc4-4b072c6735b0")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("0f476308-5114-42fc-962f-a5ff0c59fe77")
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

    @objid ("0ad5ffa5-bd29-4a52-adcd-f3eb88ac12a7")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("c80c29cd-397a-49cb-a4a4-7218ae1871a2")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("5946a0b9-103e-4d6d-aa32-c96de456963a")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
