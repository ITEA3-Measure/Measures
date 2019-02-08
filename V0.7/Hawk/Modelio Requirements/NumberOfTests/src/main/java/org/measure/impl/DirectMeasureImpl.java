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

@objid ("8f54aaa9-8919-4b65-845a-bc9ad496c972")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("e1730034-37c3-4bc6-b9bb-3882e4723964")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("0869422b-e1a1-44db-942b-c6536c7b0372")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("4946eff0-c94b-476a-a467-360f4e13c7b7")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("e87926d4-2358-4120-b2e9-6a851e60c900")
    private Hawk.Client client;

    @objid ("ef32e829-a269-40f2-bc39-e29b43f12ce9")
    private ThriftProtocol clientProtocol;

    @objid ("04113156-2f37-4cf2-b353-a86ebb4e8e65")
    private String currentInstance;

    @objid ("78b49488-fcad-44ba-bcd3-756149ef2a99")
    private String defaultNamespaces;

    @objid ("8bef0dd0-7a50-4a3d-beb7-3aab764d475f")
    private String serverUrl;

    @objid ("6b54710c-3ca1-4d14-9382-0966f5ca1550")
    private String instanceName;

    @objid ("3e6cb307-3d79-4840-a739-dae40b70f60a")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("1d92c380-84e2-4353-8749-feaff4ccc811")
    private String repository;

    @objid ("7bce60a2-4154-4037-9165-c9e9ff8ebb06")
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

    @objid ("ba1cbbbb-7b47-4ad7-ac6c-c02e19d53ce1")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Test.all.size;";
    }

    @objid ("c41d52cd-2266-4afd-af2d-9442a2244d19")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("2b54a5b8-b27f-4fbd-bbf6-655b2076a644")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("0a9e4dee-700c-4773-9a38-9c4a904da823")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("bd8977f6-950a-4e49-b020-dd17d15fc181")
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

    @objid ("e2463c3f-9106-45a7-ac8b-9e6651718c7f")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("7f3df05e-b09c-4fcf-ade3-29db6f785b3c")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("e7a5a00e-4bcf-4efc-b02c-1850f6a4ae01")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
