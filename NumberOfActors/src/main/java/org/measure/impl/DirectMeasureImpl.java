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

@objid ("bb5c0adf-6bd4-44a9-b1d4-2a8aee977e74")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("362604b7-7efe-4d2a-8476-0017953d74c6")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("f8edd398-bd18-42de-acb2-a0ea2998fdfc")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("9bbbf18b-3105-4cdd-b748-3a080636924e")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("72989ffd-0aea-4dba-9ccb-884f285d7faf")
    private Hawk.Client client;

    @objid ("23647d60-a9c8-4651-b717-722d7cc21676")
    private ThriftProtocol clientProtocol;

    @objid ("18941e67-f906-4212-9b8c-81007c9ef5af")
    private String currentInstance;

    @objid ("bb92ea18-cd47-4106-95a7-5d3f45fe78d4")
    private String defaultNamespaces;

    @objid ("6eb7151a-0b73-4902-a208-f34c908097f1")
    private String serverUrl;

    @objid ("cc06b244-58e7-44fe-a55d-97a7b0a6419e")
    private String instanceName;

    @objid ("8dd39162-4443-4c60-80b2-4ce26838adad")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("586e3909-5b30-4b65-b9ba-31cfbf19d0ae")
    private String repository;

    @objid ("800eef7e-2714-4f75-98d9-b532dbd8609d")
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

    @objid ("60a8c8fc-7be2-446b-b2bd-84af24b39a7b")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Actor.all.size;";
    }

    @objid ("f1854bd2-c762-4034-ab8b-967701117704")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("82d8c6e7-7164-4516-876f-15e469bd7e33")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("401ae85c-5364-4f31-84ce-19cbf3f4a6e7")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("05aa0fa7-9a26-4049-8893-589e3980222c")
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

    @objid ("949f56f5-e64f-4b81-8389-0c848cb01690")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("6df00cb0-dc65-4d23-9b40-58e674ee5482")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("3b0606c5-c787-4ec6-9dc7-f38386372aac")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
