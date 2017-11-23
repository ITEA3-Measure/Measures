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

@objid ("fd640aea-ba76-4558-94ce-4025b66be59c")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("e4a102ee-c2d8-4cca-a14d-1e3c515a09aa")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("827c2434-efb0-4c47-a2a7-27950ccea4d6")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("46f08631-a335-4dd9-bbdd-e6af847758c7")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("03ca2d91-f8c5-4390-9e96-a368eca34921")
    private Hawk.Client client;

    @objid ("dda96130-d65d-4673-b4a3-1523429659be")
    private ThriftProtocol clientProtocol;

    @objid ("fe40b0b1-f7ea-4182-b504-5a76fb0b6d43")
    private String currentInstance;

    @objid ("ef5c4337-1e1b-472e-b400-879e3d4ea3c5")
    private String defaultNamespaces;

    @objid ("81f3c7ae-4512-40aa-a984-3dcbbac067b6")
    private String serverUrl;

    @objid ("ee1670b2-6604-4103-b4bb-8ec3cc0e9944")
    private String instanceName;

    @objid ("0a00cd27-c5d6-4afe-b9a5-964d904fc2cb")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("607740d4-4548-4096-9c81-d68e26cfad69")
    private String repository;

    @objid ("8e8db03a-134b-425c-967b-5ba821ee8f1d")
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

    @objid ("ee331041-9310-49bf-bb97-1f026d691e6b")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="var nservices =  Note.all.select(n|'@ComponentParameters'.isSubstringOf(n.Content));"
        + "var result = 0;"
        + "for(nservice in nservices){"
        + "result = result + nservice.hawkParent.OwnedOperation.size();"
        + "}"
        + "return result;";
    }

    @objid ("dcc2da90-471e-42ae-a56a-7834d1d3bb45")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("b3c32c78-82e0-4631-96d1-1996698bd9f5")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("2abb9e42-36d2-45e2-80a2-1ee74718123a")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("700419ab-b6c7-4e8a-9cde-c6bf78a32f97")
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

    @objid ("856b1d63-6f2f-4115-bd2f-bd210326f236")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("4f410494-20bf-4037-a348-7eaa5448d1ed")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("9ae1b6d5-3420-4db5-8beb-aca49caa01e7")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
