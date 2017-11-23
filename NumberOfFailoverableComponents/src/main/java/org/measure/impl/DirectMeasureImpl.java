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

@objid ("10ec19e2-cf25-4159-93d3-26f53ea9eb39")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("0849ecd9-5000-4e39-83f4-73a43e80dc52")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("fa187b16-1690-4451-847f-b9a693404a29")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("598c8d00-ec92-47d4-aef6-3e63d132ff70")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("9855996d-05f6-4355-98fc-964e56b3a99e")
    private Hawk.Client client;

    @objid ("a5966af8-8974-4ac7-a23b-72821e588d60")
    private ThriftProtocol clientProtocol;

    @objid ("0fc18d3d-2dab-493f-9cc0-071d4b2e1c15")
    private String currentInstance;

    @objid ("12612857-92a8-4277-9878-65ab389ac619")
    private String defaultNamespaces;

    @objid ("e42f23b4-bdf8-4e2f-be6d-f18c063c1e5f")
    private String serverUrl;

    @objid ("9c01ab3d-515d-44e6-a830-5ba10b010414")
    private String instanceName;

    @objid ("f7443c9e-20cb-4269-ab5b-b7630a17f0b7")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("95e53d0d-c739-47b8-aeb9-6aac79489dcf")
    private String repository;

    @objid ("f60a3ba3-92f2-4fab-a153-6e468c4455f2")
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

    @objid ("b48a4319-4001-4f35-ba20-f3ffc0689c27")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Failoverable'.isSubstringOf(n.Content)).size();";
    }

    @objid ("a6934121-0d4b-4524-a299-120089339a09")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("a9f23063-6c1c-4599-bbb1-ff22225f6e46")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("197ae7b0-b22e-4337-b5f0-4fcf1a5b2768")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("f5b5eff7-b945-45c0-97d1-1837ace47313")
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

    @objid ("1e09e7de-2a53-4b92-a9e0-d4564bdb651d")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("3c95d49a-7832-447c-9098-c06252113049")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("a3d00eff-109a-4eec-a8ea-c8aaaa18b851")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
