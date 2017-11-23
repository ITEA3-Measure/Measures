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

@objid ("d4d3bfe1-d5dd-4d72-80eb-b3319cba532c")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("7c187177-c694-4a58-a369-7062a0511854")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("8db0ed55-dc60-450d-893f-aecbc70f236c")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("880c34de-d5f2-48ff-a552-4fc0bf8fceba")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("700afd53-0718-4e5e-9293-3f99a3ca52a1")
    private Hawk.Client client;

    @objid ("578533da-0bbb-472a-b41f-f9896beac92c")
    private ThriftProtocol clientProtocol;

    @objid ("68ff6848-c632-4d91-94b7-376e0ced3311")
    private String currentInstance;

    @objid ("8bfd1fb0-5bc4-4d42-a10c-e4bd2774c1fa")
    private String defaultNamespaces;

    @objid ("2d87cc2b-205e-4228-aae5-778990dd8eac")
    private String serverUrl;

    @objid ("e52535de-34af-4110-9b6f-9b99a57b5142")
    private String instanceName;

    @objid ("d14b2d5e-fccd-4ea4-9b79-86055e4d27fc")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("61e1a9d3-1e28-4600-a859-1c50f93ed232")
    private String repository;

    @objid ("c7ccdcdf-d091-48a8-9862-6dc52da41f2e")
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

    @objid ("cabe3b7d-3568-4faf-9fcd-8c87b5bc91ef")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Interface.all.size;";
    }

    @objid ("619cfea2-cff0-4e86-be02-e8203ff6b081")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("fa6c394a-047c-4740-be32-c49f15871165")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("05d748d8-8e24-4a52-8453-90c0c08757a1")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("5bcc8771-ad19-4d48-b9cc-75aaa3ecd34e")
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

    @objid ("6a02e515-dd2d-4a48-944d-9da937798b24")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("6a66de46-dfd7-4470-8ebd-2718472ffd9b")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("dae90924-938f-4414-b377-27d1d310b559")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
