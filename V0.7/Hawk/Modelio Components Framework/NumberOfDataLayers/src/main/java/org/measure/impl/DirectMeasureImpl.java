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

@objid ("962dba6e-3c2f-49bd-bae0-29d48299e8d4")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("87061f0c-3230-41b2-933a-63632dc046fd")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("c9c4af15-cf29-41bd-b603-df8d373b65eb")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("b622ed76-587b-4e91-a386-408b83490d10")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("26862e03-eb95-427c-bc0e-5c213fd51ada")
    private Hawk.Client client;

    @objid ("cc1a5237-af71-41a8-977a-3659ef0e3703")
    private ThriftProtocol clientProtocol;

    @objid ("c47f1ff8-18c6-4460-8cbf-60a62f6cb167")
    private String currentInstance;

    @objid ("4c232cb0-3197-42c7-b4d5-54e26a06a856")
    private String defaultNamespaces;

    @objid ("3cfb0f54-a727-4a76-9975-c80f39f215fb")
    private String serverUrl;

    @objid ("5e984063-9e09-4b94-89ef-583f7912f515")
    private String instanceName;

    @objid ("8ea76182-d9ca-4aef-a2a5-e976665745e9")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("d5a3807b-6b1e-4791-9f5b-90249ca43822")
    private String repository;

    @objid ("a7ef640d-e26b-4224-bdf1-849e7149c75e")
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

    @objid ("d2dde8ed-1a4c-42cc-b606-cfdf4cbdeeb0")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@DataLayer'.isSubstringOf(n.Content)).size();";
    }

    @objid ("0c719077-c963-47fa-99e2-9e8c99b1d83c")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("fcfe8f31-0253-4d29-a80c-c2dba95758b9")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("90acc8c9-a30d-48c1-8107-ca102c825d84")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("93f939a0-81de-467c-a25d-eb055060f434")
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

    @objid ("80e89c12-9b6b-421a-92ca-58103672f6a7")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("34dba6b0-6897-4512-aaa1-53753d7d2fad")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("ed438baf-fe4b-4e95-9909-553613a59e76")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
