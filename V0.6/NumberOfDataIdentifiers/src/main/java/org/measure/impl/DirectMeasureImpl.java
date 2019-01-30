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

@objid ("7ceb640e-9741-44e2-b10a-0b80c2bd301a")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("357d81f4-9a1b-4db3-8610-e1e55b035c33")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("b27a917e-e9c9-4779-a996-4593f8bd78cf")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("f2dbdda4-d277-4d2a-8847-61c39b8ba9ed")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("932fa465-1fba-4e27-9b2c-bf09f4bcdc44")
    private Hawk.Client client;

    @objid ("e331a36b-c8d0-4606-b0a9-4a01f30dd542")
    private ThriftProtocol clientProtocol;

    @objid ("4b2472e8-b9d2-476a-9af8-a1b21f961460")
    private String currentInstance;

    @objid ("8859f0ca-9298-4cf4-b664-cb00fa09e631")
    private String defaultNamespaces;

    @objid ("3c5418f1-25f7-4c39-a7e4-84c5d5badc41")
    private String serverUrl;

    @objid ("62befaf1-428d-4602-9a70-a3c22b1b642d")
    private String instanceName;

    @objid ("e706004a-c4f5-40c2-a1af-e39d060f9c23")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("1c6dd3c3-d906-41a9-8362-fd738a168bc5")
    private String repository;

    @objid ("5208a4da-94fa-4187-8613-c06654bc429b")
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

    @objid ("887ee3b6-a2c1-4117-a40e-1ea5a7fdbb0d")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@DataId'.isSubstringOf(n.Content)).size();";
    }

    @objid ("587fefa6-adb2-4693-93c5-66c907e91d04")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("163d4358-62d9-4a90-8c01-a98e35c4df0f")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("3929b388-d35d-46dc-a80a-0c271b89085e")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("58aca580-9e0e-4ba2-857b-710278a1806c")
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

    @objid ("bdbca385-61b7-4e5a-b920-02c22371d365")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("3499fe9f-36e9-4399-9a45-efa30d5a41f5")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("be411cd0-aa3f-49e7-a89c-4fe82b33aae5")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
