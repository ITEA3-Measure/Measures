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

@objid ("2327973a-d1fe-4f81-b288-9372de89f9df")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("13ca9236-15cb-4fde-9494-397b14b299ff")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("f8355692-28fa-4a74-8015-3b634a030093")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("755c50d6-4d2e-4b32-84cd-c8365c17b9b4")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("f1ff51bf-8225-4e6a-8477-a19c075b9ddf")
    private Hawk.Client client;

    @objid ("6c6b5614-d245-4e76-9fb2-f0d7a8facb68")
    private ThriftProtocol clientProtocol;

    @objid ("055014c6-bee7-4b3a-9440-7d0c662fbcbd")
    private String currentInstance;

    @objid ("448d3e96-38a8-4726-821a-93f8e1aa79a3")
    private String defaultNamespaces;

    @objid ("f4605a05-d1bf-4e0d-bca7-4b10c5f68d47")
    private String serverUrl;

    @objid ("2692f7e3-7120-4204-917a-00a6f2737b6e")
    private String instanceName;

    @objid ("aea3a3cd-1545-4229-9fa5-b8594cc9c434")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("041abe7d-140e-46fb-bfca-b9911228a547")
    private String repository;

    @objid ("814332fc-aca9-48bd-83b3-75374485afbc")
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

    @objid ("f78b67f3-5fed-47be-b390-991dd23a4101")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        //Number of Requirement        
        //Total number of Requirement defined in the selected scope
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query ="return Requirement.all.size;";
    }

    @objid ("422f61b7-8f02-4c8c-9ff8-fc84e1c0fe4b")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("290fc9d7-7700-4de6-b149-c6a9b8a41a9e")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("e3dff71e-badc-4070-b55e-943f32dfe60f")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("bf455f29-52f2-49f9-8f6b-d178c48e8fb1")
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

    @objid ("bd2922c8-6edb-4e74-b377-023e11d69c9f")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("a96ed2bf-db0e-4c90-82c1-5af6aff8ebe8")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("e70e718d-14cc-4596-83fd-d8394ef737cf")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
