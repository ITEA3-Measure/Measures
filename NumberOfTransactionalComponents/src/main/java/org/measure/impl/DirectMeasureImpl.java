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

@objid ("d118aca3-e72e-4fb7-a9df-e20101a1d545")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("7181a65e-bf78-4a43-a791-745c2c01b348")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("315cb885-a7d4-46b7-a9d0-057217adbbab")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("d74e5220-dbfb-42bd-962a-8a65b2d101a2")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("41a65b4f-db10-40d6-b45d-8cfa78ab9d56")
    private Hawk.Client client;

    @objid ("fa8af3f5-7d8e-491e-a284-2f66adf38c2a")
    private ThriftProtocol clientProtocol;

    @objid ("a7dbbecc-0f3a-4ded-99dd-7cc3b936079f")
    private String currentInstance;

    @objid ("481ed9d5-3e28-4992-9677-18de6b5a43a3")
    private String defaultNamespaces;

    @objid ("38c90e1f-448d-4001-98fc-d2d092073f44")
    private String serverUrl;

    @objid ("b5684ee2-6772-44e7-b6b2-cb3a6abf3171")
    private String instanceName;

    @objid ("d7934663-d96b-48b8-932c-1506b72068c3")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("6ad621c6-3675-4053-8e02-d6eac833e39a")
    private String repository;

    @objid ("f4bd8453-d850-46e8-8499-6c527abffa91")
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

    @objid ("ca258302-142d-48ff-99a5-32f860e72fe8")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Transactional'.isSubstringOf(n.Content)).size();";
    }

    @objid ("c4ab5001-4764-49b2-83df-2a567dab1a02")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("a003c4a7-886a-44a2-bac6-0f72ac27df87")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("152cadce-5e39-4572-9df1-2762e32380b0")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("7ea8e0bb-b3f9-498e-8d01-2fe4e28a08c3")
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

    @objid ("a07a36be-e8e1-4014-9ac5-f5ba4ed834df")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("efe4193f-f3fd-4710-867f-d123a386def1")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("1d5f7c05-b5c6-456f-b6ff-b2ddb5800f2c")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
