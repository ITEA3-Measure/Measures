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

@objid ("62ea6f2a-a62a-4f0d-ac97-c03fa0b8484a")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("7bfdf347-4c61-4625-b49b-5b596ed9ccfe")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("313a47b8-6654-45ba-b530-c7ac7bd41674")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("ee5a5532-0331-4c10-8ecf-36bf2fc3a159")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("6ef064f2-3e4f-41f3-8691-d778ea1939b5")
    private Hawk.Client client;

    @objid ("a10454b5-03e3-471a-8dcd-1433cea472c4")
    private ThriftProtocol clientProtocol;

    @objid ("b256d7f1-8b75-4a8c-a8ac-c9601bacb5a0")
    private String currentInstance;

    @objid ("08be1ab8-0e8b-4f0e-9fe2-1408d16e2943")
    private String defaultNamespaces;

    @objid ("bce8a215-c3e1-4f13-acc4-9832e0dd818a")
    private String serverUrl;

    @objid ("b66c403b-1e7a-4555-a490-ef2365b9c33c")
    private String instanceName;

    @objid ("42c7737e-01f8-46e5-97d0-fdc97c08bf92")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("6a7f2db7-5240-464f-812f-f391a2e01a6b")
    private String repository;

    @objid ("72e31b9d-7320-446a-96ed-b4c0c4bd1523")
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

    @objid ("441bd73e-e0be-4839-adaa-15b2e451fa90")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Synchronized'.isSubstringOf(n.Content)).size();";
    }

    @objid ("ffdd55a9-5032-4e66-8a9b-b6cfd340c22e")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("cf6ce45b-5937-47bd-b4d8-77cd9ba2c7b9")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("0232b49c-215f-4a4a-a451-a8e8baa4e21f")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("f05b4caa-4296-49ab-8035-3fc9706058fa")
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

    @objid ("f8037c46-b3c1-4cd8-b87a-cf7d0366fecf")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("07a8fe4c-3eee-4f01-8601-a8baff01ed96")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("fd3567b4-6e17-4487-8ba4-892d37bd2169")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
