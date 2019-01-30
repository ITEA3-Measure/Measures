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

@objid ("53caea8d-f867-4d41-ba20-f94a12b725d0")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("028fcad7-0de9-4e9d-9a72-0987bcefe68f")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("c0c54597-cec7-4894-881c-9474a073ec8d")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("af6161f0-b739-4fbe-b2fa-3760f5f688b4")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("58f9b501-1895-4620-9e60-c807bea2a38c")
    private Hawk.Client client;

    @objid ("7da6e5c3-d175-497a-b906-691c9cfe57b7")
    private ThriftProtocol clientProtocol;

    @objid ("f2e7ce3e-4db9-4032-b23a-4947b4718c48")
    private String currentInstance;

    @objid ("5b281a51-2ab2-429c-8cbc-b6ef06ff0b4a")
    private String defaultNamespaces;

    @objid ("df0f2679-3357-40cd-9451-1f70e6659b3c")
    private String serverUrl;

    @objid ("d04528f9-eec4-4007-8134-3eed36d69993")
    private String instanceName;

    @objid ("5e7b448f-2ec8-41f2-90d2-eb46f4865aca")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("c5d8a56f-4069-4a97-8077-c53a06fcaf68")
    private String repository;

    @objid ("39b8ceba-71ca-48b3-ba3e-c128e08ed8f5")
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

    @objid ("f47153ee-0914-44e9-b51f-887915178c7b")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query ="return Goal.all.size;";
    }

    @objid ("fa8bf94f-0651-456e-8182-364a35192557")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("bdad30f6-94a2-46d3-8aa0-f9830ca7f2f7")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("0c756f27-eb88-48e5-8a92-f866ffc99b62")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("1bf36fb2-302d-49b2-bd1d-44b65d66bb99")
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

    @objid ("d738d8a9-cdb7-4e52-b7ec-ca26ae26736e")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("075b384c-53d5-4bd6-98ef-3d8bae71e258")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("835c110f-7cc9-46dc-8242-45ffdb77e0ef")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
