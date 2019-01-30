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

@objid ("2e2d0a29-89b9-48fd-a015-8c8969ab00be")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("a5bdaf0d-893c-4091-b9b6-e3617359d180")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("bc856733-fe34-4ccb-8998-9781673998da")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("1c224f61-c72c-4263-906e-64e1882b9025")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("93e15c17-6832-45cb-a8b0-03d6c9b760dc")
    private Hawk.Client client;

    @objid ("95877491-b09c-4cea-a3f0-9a9b6808fd05")
    private ThriftProtocol clientProtocol;

    @objid ("0b150250-64b2-412b-b986-0e186e0a9da1")
    private String currentInstance;

    @objid ("df97b9a8-c785-4fce-8e71-bea4b4089b17")
    private String defaultNamespaces;

    @objid ("31c148c2-0b30-4707-aee8-4a3dfedd40e2")
    private String serverUrl;

    @objid ("a7b8cafb-466c-49c5-ab8e-b644431b778a")
    private String instanceName;

    @objid ("697c4d85-f509-43e6-a429-6ecbc4ac5d7c")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("31b5af45-fdad-4578-96a4-8f7f3c1569ee")
    private String repository;

    @objid ("007c39cf-e19a-436f-ab6d-c61f534f0093")
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

    @objid ("b7d27918-09b8-43d5-a404-3a75dd2493b8")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Attribute.all.size;";
    }

    @objid ("f223f37c-f164-411f-9c45-00eb99e494c3")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("d352a5fd-28bc-4711-af5f-45b58f23c11f")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("17767fc3-cfa7-4a58-b8ce-3acac3631a01")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("06d436d8-634e-4f9e-9e40-2f1d43fd84ba")
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

    @objid ("cd482e8c-d70c-4302-8e2e-b36d3460da63")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("17131533-52f8-4e8a-b3fe-c2c63980660f")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("ac4c69b4-e919-444f-8576-102280d5f873")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
