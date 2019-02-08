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

@objid ("281dce6e-ede6-427f-8644-3dae302c5e56")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("c353b31e-c986-4521-8fcd-070da7b24ba2")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("1205aad4-3347-4084-99e3-813521d31443")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("e3a0c014-ede5-4865-a532-d7e3865522ee")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("726eff6c-27df-4f53-b7c4-d148fa792bf0")
    private Hawk.Client client;

    @objid ("d92726ce-859d-40e5-a41a-f3ae4f6a9489")
    private ThriftProtocol clientProtocol;

    @objid ("832c1208-ae3e-4ae3-8118-7b5c40bda3b7")
    private String currentInstance;

    @objid ("b3b0eb65-af20-46ff-b4fc-21451dbde033")
    private String defaultNamespaces;

    @objid ("121cc67b-bb43-4d9a-820e-023bf1074cba")
    private String serverUrl;

    @objid ("bafe9b41-fff0-4f1f-9672-4a07b0a919c0")
    private String instanceName;

    @objid ("1a2ea795-60a2-4a90-8518-0cc6257944f7")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("e8fdcc61-4372-4c3c-9891-975e00af7a29")
    private String repository;

    @objid ("2655d150-d155-43ef-8193-81bceda26674")
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

    @objid ("c5da4748-7e67-48ee-9313-97e96eb9105b")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        
        this.query = "return Note.all.select(n|'@AggregatedComponent'.isSubstringOf(n.Content)).size();";
    }

    @objid ("cdd18d5d-788c-433b-9bce-df7f9857fae4")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("65196954-8b00-4478-8946-4f55204ceded")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("c0996e45-b6d2-4114-8c49-c1bb3fcd202d")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("679da047-d70a-4d72-8fcc-31e875a85ad2")
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

    @objid ("51adf9c5-80d6-4cfc-aacb-fc49cf08d1d1")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("790beab6-80f2-498d-8f15-565f3f9c63e1")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("a9cb5b81-c7e3-4e29-a181-cbc146a18c1a")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
