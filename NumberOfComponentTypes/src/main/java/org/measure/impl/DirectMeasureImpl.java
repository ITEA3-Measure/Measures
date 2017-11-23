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

@objid ("e609d44f-3ec9-4cc7-a515-bab03ae296c1")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("d56b1735-0e47-4ada-84dd-058011e5bd5c")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("99935c37-71d5-4fd1-a6dc-4b1849be7281")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("72824ee8-5fb9-486f-ad85-ca182a52b05c")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("8f7dbd2f-c32b-4db8-91e1-1ef63aa38eea")
    private Hawk.Client client;

    @objid ("edcc30ee-de83-4e56-a13e-19a2c3f8e16d")
    private ThriftProtocol clientProtocol;

    @objid ("8e34c256-5bbd-4593-ac31-cbe7dd49760f")
    private String currentInstance;

    @objid ("30540e90-87c3-466f-a7d2-7f8b207a4656")
    private String defaultNamespaces;

    @objid ("c7d7d29e-67a0-4855-aaa5-86f6161d9786")
    private String serverUrl;

    @objid ("1ab9a7b5-0e35-440d-8df4-ae758723b36b")
    private String instanceName;

    @objid ("8dd4412c-dff1-4c91-b1ce-14fe7b93937f")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("4804b5d8-d45f-437e-b526-85a33e106a07")
    private String repository;

    @objid ("be8fabc4-d360-41e3-83e6-8045bf0360b1")
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

    @objid ("5547e696-1c17-4c19-8042-f6509d6c1b52")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@ComponentType'.isSubstringOf(n.Content)).size();";
    }

    @objid ("61d97dd7-2afa-425e-9b33-fccb3e5c6d12")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("960227cb-77d4-4c2f-bb07-619e215e9ba4")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("df6f6696-9f91-4ed4-b104-dd7d2c594aa8")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("39dffbb7-43ae-4267-94df-7803c0680f3f")
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

    @objid ("5f00ba54-1eb3-4c6a-a5da-62932c4fe59a")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("415958be-595c-4f17-bde3-dcc87e924be5")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("23ca4bdd-f084-45fa-b1a9-d9b119a72fa0")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
