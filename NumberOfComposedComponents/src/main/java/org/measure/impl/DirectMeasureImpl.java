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

@objid ("54f6a81e-a9ba-4cfe-ba35-57ab3e41ef5b")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("49362f50-4dd8-442a-829d-162c2d1381c4")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("e6023d25-9b00-4abf-8f01-681b5422ee35")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("b09f8d1d-0309-42c8-b816-0c62ad9f60c8")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("b81c6ae5-2adc-4275-9be8-d53e65f5b65e")
    private Hawk.Client client;

    @objid ("27d33820-9463-4406-88d1-c18e3642465f")
    private ThriftProtocol clientProtocol;

    @objid ("c4bd1787-420a-48ea-ac27-b8edb156e01a")
    private String currentInstance;

    @objid ("e6adac97-d80a-4008-87b3-58dbf9be0db3")
    private String defaultNamespaces;

    @objid ("6e11fc7d-6889-49a4-a651-83aa4f105254")
    private String serverUrl;

    @objid ("478aa930-85cb-4d33-8f63-78036dd8c848")
    private String instanceName;

    @objid ("d921973a-bc04-4241-ba2f-80c8de929963")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("9c53c0f1-c5f3-48aa-9d8b-3936d035fe03")
    private String repository;

    @objid ("eb13fbdd-de91-4168-a9ea-a61822776b38")
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

    @objid ("a6d53dee-2e9d-40b1-8cd3-441558be0e2f")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        //Number of Requirement        
        //Total number of Requirement defined in the selected scope
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        // Hawk EOL Query
        this.query ="return Note.all.select(n|'@ComposedComponent'.isSubstringOf(n.Content)).size();";
    }

    @objid ("c77d188b-2edd-4bab-8c93-f0c936ffd244")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("a646f390-099a-4544-bd73-1ddcf5d5290f")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("9623c344-094f-47d7-a1ee-56135e007c63")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("7e8813b0-df45-422e-a66d-b18f353fdb93")
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

    @objid ("51225dc8-cbea-4f4b-8703-d4628b3adf75")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("71a48f8c-d3f1-4393-8e21-21da9a02be4b")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("0c9c4348-6d69-4b43-8b5c-a0dbb1178ad2")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
