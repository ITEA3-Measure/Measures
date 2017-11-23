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

@objid ("e67d2d51-6d2b-4dcb-afec-28fc57a960f6")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("2bed1c7f-a309-40ae-beab-fa5dc1686272")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("20d2cf6e-755f-4883-b1bc-c460b5ff3ef1")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("43bc7280-edf3-4685-a51a-2cdff233b89c")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("bfca2b32-1fdb-4328-be1a-48dab558043b")
    private Hawk.Client client;

    @objid ("fda0077a-3762-4a37-a643-4d3145811b93")
    private ThriftProtocol clientProtocol;

    @objid ("7aa81cf6-cd87-4819-9ada-97f9975b19da")
    private String currentInstance;

    @objid ("021e45ad-1a6a-437b-ba5a-a13642ffc419")
    private String defaultNamespaces;

    @objid ("13a7078c-4bbd-4073-a3da-82c6190251c2")
    private String serverUrl;

    @objid ("7af4e97f-8f4f-41fa-9c47-e40933075aab")
    private String instanceName;

    @objid ("f0cbb1e3-c71d-4339-9074-92350e643806")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("37183213-ac62-4387-8dcd-2631b4e922c0")
    private String repository;

    @objid ("3452a231-4f96-4fb8-b780-26f422cb4e8e")
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

    @objid ("a8eabbff-25f6-4bdf-8a5b-95159c70094a")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@Data'.isSubstringOf(n.Content)).size();";
    }

    @objid ("9faf4d6b-cc02-481e-ae78-4b9e3f1e9dd9")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("d7694b39-0537-41a4-b198-50b802cf7ab2")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("fb2031d7-7f8f-417b-b3a0-02a0432b00ba")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("3d30605c-34b1-4d22-8b9c-80090c28ffd3")
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

    @objid ("2d610ea1-ac1b-40fc-87af-f65deb20e9fc")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("6dd46f53-d95a-4137-8412-c930e3ba5482")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("33ef063e-aa83-41a8-bf1a-4c49daed4ee3")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
