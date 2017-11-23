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

@objid ("9c334756-ce1d-451d-ab7a-143408c2fec0")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("658048b0-05dc-422e-b8c9-7aee11f58319")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("8a85baba-9bd8-4fb7-9fc2-e093418f30e4")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("296ed056-7ebb-46ed-880a-3f72f3c9e6f7")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("601932eb-60cd-41dd-9a74-0b7eee4cada8")
    private Hawk.Client client;

    @objid ("3ee6d52b-c033-4084-927a-16a3ddca6935")
    private ThriftProtocol clientProtocol;

    @objid ("f01110d7-28cf-43c8-9139-5112731683c8")
    private String currentInstance;

    @objid ("ff0a7c4b-fe37-4879-a95d-2275aa8cd92e")
    private String defaultNamespaces;

    @objid ("2c06cd90-f6a3-4b22-a88e-d099e1ffc73e")
    private String serverUrl;

    @objid ("91ed54d4-9448-4341-89df-2de939a5e8e1")
    private String instanceName;

    @objid ("e053b5a9-1da9-49a9-9dfc-da3f7dc21b5b")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("8dc3a5a5-7977-425e-9571-212972efb5fd")
    private String repository;

    @objid ("2aaa339a-46f7-4159-81ab-da2a6c9f1b72")
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

    @objid ("278c3ee5-675e-46d1-a88f-57dfc576856e")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Operation.all.size;";
    }

    @objid ("999f35fd-f66e-4693-bdca-e58e2046e15d")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("3cbbd4a4-332d-4d3c-b8bf-67a70dc1bfe2")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("d3166950-d550-403b-ae72-3b71095cd798")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("03dfaf56-0233-435e-8048-0ace5fdb2353")
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

    @objid ("28339abf-faa9-4567-b000-d5dfb464327f")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("d99fb881-d6dc-4b75-ad29-52acb3ebc4c1")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("34f536f2-372c-4362-b975-689a48efafe9")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
