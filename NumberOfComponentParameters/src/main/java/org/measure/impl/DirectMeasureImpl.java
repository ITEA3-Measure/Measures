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

@objid ("bf32f6f1-ab95-4c0d-96f3-53b02cb4f406")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("4b7a5259-493c-41f3-8d72-7d287ae0912c")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("98b94168-1777-41f4-b55e-b0d61dbd363e")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("30bc5aa7-f519-42c4-9903-09df6b9f0828")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("37ca590e-39c3-4074-aa1e-5679f2704155")
    private Hawk.Client client;

    @objid ("647ff852-f9ed-463b-8080-a5bbae75598c")
    private ThriftProtocol clientProtocol;

    @objid ("e326d8b9-ef74-4c27-97aa-9c0929b46f88")
    private String currentInstance;

    @objid ("8fccd1ea-d391-4256-860f-2903f1b1fb55")
    private String defaultNamespaces;

    @objid ("af57c562-080a-4fe3-9fb5-fe5e15dae96f")
    private String serverUrl;

    @objid ("3c7b0bc7-db6c-484f-a789-85cb13e414de")
    private String instanceName;

    @objid ("190297d3-f731-470c-99aa-6b8cd65f72c5")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("a0f8948c-2b99-48e1-9e90-26d15c09b129")
    private String repository;

    @objid ("cb156fe4-3629-4e4d-9f67-42d1e58392fa")
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

    @objid ("3eb893a2-f8df-4c77-b018-9492f829c17d")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@ComponentParameters'.isSubstringOf(n.Content)).size();";
    }

    @objid ("a81b42d7-af85-485c-9572-4882e42cc835")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("0df79056-0f49-407e-b614-fa7c44307cf0")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("4e237554-5e10-495d-b73f-2f2ffc064ae8")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("322d0b95-a505-4aa8-b346-6e585c0381e5")
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

    @objid ("fe641a4d-f1cd-4f90-a94b-0e7ded39a3a4")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("84480167-1d31-4c06-be79-5f354439488a")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("8cbd7055-5340-4ebb-ba47-c5ecf7f0b6d8")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
