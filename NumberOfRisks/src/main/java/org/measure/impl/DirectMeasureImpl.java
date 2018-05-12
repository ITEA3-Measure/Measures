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

@objid ("977af043-3c6f-4ad6-b236-1f9cb3cc7bb8")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("81f89472-98cc-4a50-8464-f16dc0e93679")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("1b00ab79-363c-4112-945c-a13054eb9524")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("bac6c73e-7be1-481b-9381-a8118803a146")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("1e915fe4-ab01-4865-a770-4bc6e3f7e693")
    private Hawk.Client client;

    @objid ("1f3bbad0-4faf-4986-a260-2ae93d1ef470")
    private ThriftProtocol clientProtocol;

    @objid ("7553624e-2634-4257-b8a5-3343b9ab7240")
    private String currentInstance;

    @objid ("ae2aa05d-418e-4d65-87cd-b4fe807af372")
    private String defaultNamespaces;

    @objid ("ca03a4e3-1dad-475e-8ed0-26fe11739ea3")
    private String serverUrl;

    @objid ("9b7d1d4a-6f64-4b36-b8d7-a98ffe0b2250")
    private String instanceName;

    @objid ("1a297534-29c3-4693-9acc-dee55852a1df")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("027e22dd-0d1c-458a-8041-6e5cd5d52877")
    private String repository;

    @objid ("f9d0cfea-b133-4674-992a-ad65746563ab")
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

    @objid ("f5b9eb87-a9fd-447f-953d-82870f1ae3a9")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query ="return Risk.all.size;";
    }

    @objid ("06d32af1-82e1-4558-bf47-9adc9c410ba8")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("abd4c9da-b20d-403f-90fb-07f95873c2e6")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("d1337025-8dbb-467c-b370-4976b370568e")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("00daea82-fea7-4c56-93ee-003b8a90577a")
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

    @objid ("e9dd3fae-b58c-479d-80bd-bfbe4a943516")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("9c9fdf12-6557-4f99-a1f8-cd3d7ee330cd")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("1a2436a2-24ce-4820-8cdf-776442c67fd6")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
