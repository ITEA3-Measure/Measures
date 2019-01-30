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

@objid ("9ac3fa64-1796-4743-a5dd-9073ac85c1f0")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("03365554-2635-44c7-b5af-fab8caa266fa")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("417cedf5-66f4-492d-983d-6fa2f04bff78")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("12444eb9-b4a0-4996-b4e6-4ded00087b2d")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("9b3a6268-f22f-4584-b724-71bd2cc7c6a5")
    private Hawk.Client client;

    @objid ("a04a0e22-3090-4bfa-98b9-34de001748ef")
    private ThriftProtocol clientProtocol;

    @objid ("b31527ff-4b50-4da0-a12c-e6080bcaebae")
    private String currentInstance;

    @objid ("f1853697-f402-4baf-9580-cbb5a951ff06")
    private String defaultNamespaces;

    @objid ("9766a212-7517-46cf-995c-c3107e7eb9df")
    private String serverUrl;

    @objid ("77750c5d-4af4-44fe-947f-472682d8ea85")
    private String instanceName;

    @objid ("663c3d65-3784-469e-96cc-9261d332aa0c")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("0ffa9832-e67d-462f-a4d3-4b8db2731756")
    private String repository;

    @objid ("f047a8e7-4d9b-4a74-a38d-41cb6e41370d")
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

    @objid ("37902da0-246b-4839-8702-f0918201e924")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Class.all.size;";
    }

    @objid ("5a17efee-5e7f-44d7-9adb-1a7a89a2012e")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("55a35fc6-3fd0-4a8b-8ffa-cffe3a522e4a")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("237c8785-e547-4428-b013-caa0f2c09915")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("6b2b8b40-8541-4747-8578-633a69cc5d08")
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

    @objid ("09abf79f-24d9-4a51-93c9-639c7f836334")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("513b6031-c8c6-4de9-95f8-ec54de71c628")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("29528dc0-78dd-4aee-96c4-4b31a3bf43d8")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
