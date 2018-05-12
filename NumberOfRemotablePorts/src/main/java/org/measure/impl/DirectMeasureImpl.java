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

@objid ("80d4d5c5-971a-4ade-82f7-50a93ba08b3c")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("d09a7da7-c0b4-48d8-9024-b15f921ed953")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("1a733be7-b561-4158-b16e-841f599df2e9")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("f046a184-0bf5-444f-9ae4-b4acd32c0ca4")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("6b104ffe-7166-48a0-99a7-4701be518ca9")
    private Hawk.Client client;

    @objid ("e6832194-765a-4eac-b9d4-74e18e93c3e4")
    private ThriftProtocol clientProtocol;

    @objid ("f1a73270-02eb-4871-af9a-c457c6791a44")
    private String currentInstance;

    @objid ("dfbb0dea-9318-4357-a518-9a925d096db1")
    private String defaultNamespaces;

    @objid ("3bc517f5-9463-406f-9d7d-80bf95f9e95e")
    private String serverUrl;

    @objid ("66debdee-9c00-4203-8dbc-4dccd39f6404")
    private String instanceName;

    @objid ("29b0ec23-e96e-45cf-b9ec-6472c41e8ef2")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("6af51629-45b4-4210-b404-04ea6ed48ff8")
    private String repository;

    @objid ("484a3e91-787f-414c-840a-e0b3e492890c")
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

    @objid ("2a4561cf-a27a-4548-a83d-ba21021c39e7")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Remotable'.isSubstringOf(n.Content)).size();";
    }

    @objid ("97222685-194e-49a5-b129-7bf56410380d")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("1edec512-3f68-4081-9cf8-e4d933563320")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("f0befac0-874b-4cf1-bd58-3ee91d52ae0e")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("a134a990-5a0a-4a66-8bff-99fc99023343")
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

    @objid ("dee391a0-85d1-4054-8928-79460117e9c2")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("ca5ba98b-6e42-47e3-aabc-505dafa19ce1")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("d8b51743-15ae-43a4-b460-ce2b0a98b3fe")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
