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

@objid ("298977c1-1f9f-40e3-b1a4-77c08522354a")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("2570f130-77d0-4e88-9f99-8a69d7b9f863")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("43f060af-d6ec-4da4-897b-929b6a3b9696")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("3d6f76e1-c1e4-4baa-9a1a-51cf09eff6f8")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("cabc8cd2-0da4-400d-bfac-b503722cf58f")
    private Hawk.Client client;

    @objid ("4b1ad7ed-27ca-4329-8ff3-8faead299e6c")
    private ThriftProtocol clientProtocol;

    @objid ("cb4cc797-c5e9-43f8-bdb5-4513755d70b6")
    private String currentInstance;

    @objid ("2fc225da-b554-4337-9018-badb56155f68")
    private String defaultNamespaces;

    @objid ("7369ec3b-fe36-4a58-880d-a00c21586d73")
    private String serverUrl;

    @objid ("15377b5c-5e5b-4ca2-98e0-b985b94f7053")
    private String instanceName;

    @objid ("eb5101e3-ca9b-48fe-b52e-ca40bc46e1eb")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("f49bc242-f182-4607-8ba3-a04dacaa3245")
    private String repository;

    @objid ("ad814db2-f898-4950-abc6-59b1c44cb03f")
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

    @objid ("cd368efd-5673-46de-8379-525abb57c812")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@AggregatedComponent'.isSubstringOf(n.Content)).size();";
    }

    @objid ("61084cbd-0bb6-4df4-84f9-3dc4b5cf553c")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("6f59cf94-2df3-48bc-90ff-79fbba8b6e78")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("5bf0706f-7a0b-4846-a25e-bb0090e0487c")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("6a403437-c3d1-4f79-b42a-66275c7ec275")
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

    @objid ("66be8698-efcb-4179-bb2d-18a30c9a03ef")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("4b8b2a2d-e2d4-411d-929f-54e9b9d786b3")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("e83b90be-496e-4f4e-8fcc-33770bde28c5")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
