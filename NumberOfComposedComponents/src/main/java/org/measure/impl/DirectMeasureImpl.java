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

@objid ("a654f4ba-7364-4a08-a6ed-a887c47899de")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("e99473f2-1a77-41b3-8eee-bd6363d2234d")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("32618044-7de4-463d-a3a6-f249f9d074f3")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("5afb2efe-6b60-4435-ba91-61b2c5e7fb2d")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("3aae7027-abab-472c-b2ae-c7984fba736b")
    private Hawk.Client client;

    @objid ("965aa941-ffd0-4131-94b8-cd0fe6b38cd5")
    private ThriftProtocol clientProtocol;

    @objid ("1d27ecb1-697d-45f0-9c15-84c5bd7aad69")
    private String currentInstance;

    @objid ("982e862f-f133-4a06-adea-ed31c2197558")
    private String defaultNamespaces;

    @objid ("0bc30031-aa3f-4f79-ad89-e14124a5071e")
    private String serverUrl;

    @objid ("0e94338d-f106-4e68-8539-f6ae21ecbd3d")
    private String instanceName;

    @objid ("4742bfec-6978-4872-946b-e765d9ca2a06")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("ba7abae7-2665-4c9d-8c68-58f414e0a20f")
    private String repository;

    @objid ("079d2333-0674-42aa-bed9-8ded20adbbf9")
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

    @objid ("22544d00-3dea-40cb-8891-a505701b1701")
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

    @objid ("4b17a971-3370-48d9-9893-115ab439189a")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("90314998-9e66-4a16-9049-77b94cd3710f")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("a0b492f0-a757-4d8c-ae1a-a5a4242ffb42")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("835493de-4852-478a-9fed-a0abd7660021")
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

    @objid ("f95b6b8c-028a-4f60-9923-4c23b29ac912")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("0c9627e7-24b8-4ec7-8971-d762e0596c9a")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("667f0a23-500c-4f26-8c00-aa94a1661db4")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
