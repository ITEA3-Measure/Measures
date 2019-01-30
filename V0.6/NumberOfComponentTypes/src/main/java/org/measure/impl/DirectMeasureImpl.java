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

@objid ("e202fee7-65e4-4883-97e0-abc2e69f9818")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("868cb4ce-6d70-47fa-b2a2-2d08421c7dea")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("5754be45-a0bd-4eea-8812-182f06217f14")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("a1400928-3bda-4712-9905-eb765e88008f")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("702064e4-9bdb-4878-8e0c-6ab2201ba08c")
    private Hawk.Client client;

    @objid ("22ae4b9d-a2fa-43c6-ac29-f22b90e0c91b")
    private ThriftProtocol clientProtocol;

    @objid ("758fcbdc-e82c-46f5-9693-45601b56c1b6")
    private String currentInstance;

    @objid ("f59949a7-ba0d-4f85-9049-e3251ecea3a0")
    private String defaultNamespaces;

    @objid ("3755cafa-3d16-418a-9057-0835056877e9")
    private String serverUrl;

    @objid ("097f1c48-1927-4d09-b781-eb79a939ec51")
    private String instanceName;

    @objid ("616af101-c5a1-4cf3-91b4-fa86758c2318")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("37f031a0-39ef-4406-b94d-758f74894cc7")
    private String repository;

    @objid ("cf96b43c-1287-4da3-a97a-aeb130eb5b4c")
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

    @objid ("f6a050be-facb-4d17-81a4-fd046b6b3494")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@ComponentType'.isSubstringOf(n.Content)).size();";
    }

    @objid ("a3535ce8-c073-4135-843b-1663653f3e2b")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("ec6e0111-7b75-43a0-a40d-27656fb72f7e")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("d00b2fb1-b772-4837-99b6-dc394f8996c4")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("5bff5b83-0aa7-4ab4-a954-1549eff147c7")
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

    @objid ("d5d39b7b-1eff-4b15-b181-6715e45cf3de")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("fc6f4ddc-53b9-45dc-8a1a-93184ddd8cac")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("b5dab88d-2841-465c-aa2b-e47d3e5b7e41")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
