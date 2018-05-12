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

@objid ("b9d297ba-652c-4222-8194-6910dde5f470")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("9f536ae6-9d27-4ae7-918d-72857c98b760")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("92f482dc-dc94-48bb-9bd6-3910bd03f9ae")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("176baee6-9a39-4e6d-ab74-f577f4ccc3c8")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("ae5d3755-5f34-46db-9df7-738612ba86b0")
    private Hawk.Client client;

    @objid ("77f8adbb-5fb6-477d-a8d9-1a1272ee464f")
    private ThriftProtocol clientProtocol;

    @objid ("74018531-7ca9-4da1-b28c-5817e7321c1d")
    private String currentInstance;

    @objid ("e7a1d1dc-047f-44ac-8b19-526ba7f8a942")
    private String defaultNamespaces;

    @objid ("b906c503-5ecd-4433-a3b2-58c6116f1bd8")
    private String serverUrl;

    @objid ("57bcb777-72a1-49cf-9594-b7b987250b4c")
    private String instanceName;

    @objid ("af6fd51e-431b-4116-9c46-6934971482d9")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("21c49c89-9e11-4e7f-9415-3316afc73b94")
    private String repository;

    @objid ("e19c3306-b2a5-4c28-94ac-23c393005bac")
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

    @objid ("fa6d3c71-7619-456b-892a-9afd6d72bc95")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        
        this.query = "return Actor.all.size;";
    }

    @objid ("f6f5a4de-4973-40b5-90b1-194d2c5cb121")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("d159f9cf-3818-4e47-b127-af9332230419")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("49ad64d3-47c1-4bf3-bc39-75ddd19dd1d2")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("4d5ded8e-4bee-4631-a9a5-ed8404c628e5")
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

    @objid ("a0648cb5-6edf-4e63-8cda-ba672eef86ab")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("db32a8f5-bcc6-4ea7-a130-4264ec7bfa50")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("0f8cdc9d-5500-4783-9571-2f83279c16cc")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
