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

@objid ("7f45196a-a8a2-4e81-b7b0-dbf92ca87d7b")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("4d5001c2-02ef-4b3f-ac68-9674b2cc4997")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("21d48abf-93c5-420f-a26b-51679a60d0e5")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("5ce34db1-b6a6-4977-bc6e-f735d342b678")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("8470267e-8e67-4966-a180-f28fa0cb855e")
    private Hawk.Client client;

    @objid ("12b3911a-1ee6-4919-857b-377328e8c191")
    private ThriftProtocol clientProtocol;

    @objid ("508293d5-5d06-4432-8905-617187cc28ce")
    private String currentInstance;

    @objid ("e1b00a51-1e6f-432c-a13a-3068a1e33dad")
    private String defaultNamespaces;

    @objid ("854fc852-c060-45f2-896f-4a8a6ca642b4")
    private String serverUrl;

    @objid ("b8edab61-7e1b-4c82-8775-b77b9920d065")
    private String instanceName;

    @objid ("b5b15151-e02f-49d3-8129-1e7c6388ece5")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("cd72edc9-ebd5-43c8-a13b-4759e4b168bb")
    private String repository;

    @objid ("e74d22f2-d06d-491c-b7ca-f0854b0a0e15")
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

    @objid ("b08a18eb-c066-4ff8-a6ac-1a0ff41843c6")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return BusinessRule.all.size;";
    }

    @objid ("ec22b70b-a1e1-479b-bf67-073f02aaa077")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("b01b364d-6d40-4f55-88de-682b11322969")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("61a3b2d2-1f40-449f-9200-5ea86379c8c6")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("d6165b8b-8a4f-4df7-9ca4-b15ec98bb5f6")
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

    @objid ("d4c543d2-c4d2-486d-8625-903e208d004e")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("19d3d102-a58b-44bb-aa8e-3f1f3a2f7171")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("156dfce7-ac01-4a20-a68f-9b0b59b5e301")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
