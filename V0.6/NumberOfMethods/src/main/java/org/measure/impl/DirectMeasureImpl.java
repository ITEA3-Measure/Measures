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

@objid ("052c4863-002b-469e-a0a4-f097fe7ebde6")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("d5549fc6-73b1-4227-977e-105184b01a72")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("d5b9993c-6a16-434e-bc7a-52c477d4b2c1")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("cab1fb75-6a47-45a0-9638-3f4041ccbd94")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("5993086a-83ee-4106-b94b-bbfd645222e6")
    private Hawk.Client client;

    @objid ("c3be7710-d84a-4d7b-b2fa-7ccee442c134")
    private ThriftProtocol clientProtocol;

    @objid ("c49ed62c-4ef4-486b-91c9-dc55e56624de")
    private String currentInstance;

    @objid ("58faed7f-26fc-4cb2-a0d5-8755268b8f59")
    private String defaultNamespaces;

    @objid ("ddc44117-76ed-45ce-960d-b6222fcec065")
    private String serverUrl;

    @objid ("06c89b29-e2d5-463b-a4b5-444a7b81e714")
    private String instanceName;

    @objid ("83c976f6-0085-4ef7-8758-39672c3b15d5")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("56216ba9-328f-415f-87fb-fb99313de3fc")
    private String repository;

    @objid ("0c4d1aa7-7c6a-45ea-93eb-4f5aba73fc56")
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

    @objid ("cc126f39-4c50-4c89-ab6d-e1f97b71696a")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Operation.all.size;";
    }

    @objid ("a9dc5cd4-39f1-4d7a-9f66-cd222bc75369")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("a099f4fa-ddb4-4fa0-a188-46cdaa85ce83")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("e5b7d289-eba7-482e-85f2-e567d1dc5a11")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("c9837024-ab7c-4a0b-ae7c-9c3029277684")
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

    @objid ("ddec1c24-7f50-400e-afa0-efcee10a61dc")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("3caea99c-8ec3-4eaf-9fdc-47bfeda2a6b1")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("2e1a48fc-0bdb-43f5-a47f-ac1ff6e64a32")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
