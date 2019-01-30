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

@objid ("835839c6-1adb-46a3-bb33-ab70863dab87")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("845f12b5-0fb7-489e-bd14-24aa48202ee2")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("490eee05-07f1-4d52-abcd-9e8b4bd74829")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("2cb56d2d-fe82-4c3c-85cc-301964feca85")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("dd87c8b1-b3d6-48c2-897d-9fc89fbbc2f9")
    private Hawk.Client client;

    @objid ("b4165202-ad10-4993-b1a9-660537c6f160")
    private ThriftProtocol clientProtocol;

    @objid ("064e9d1f-863d-477d-823a-89b528d80ce8")
    private String currentInstance;

    @objid ("bfac568a-ed5c-4f98-a18d-2e89c104b45a")
    private String defaultNamespaces;

    @objid ("6fdb7f0a-a80b-45b7-b3cb-16ce4f886675")
    private String serverUrl;

    @objid ("2838c80e-0291-4ecf-9175-4304a9c14923")
    private String instanceName;

    @objid ("11d9035b-4663-42bc-ba9f-54c89a0a1e77")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("71dad121-f5ce-4fb5-abd9-9deda2491f6d")
    private String repository;

    @objid ("a48653b5-c680-45fe-a7a8-7cc2441b16a0")
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

    @objid ("517a762a-3556-4e2b-9ec4-df734957030d")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@ComponentItem'.isSubstringOf(n.Content)).size();";
    }

    @objid ("895d1721-8a09-4cba-bc69-4279bab515b7")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("59c87612-1462-4909-9683-e92f88e6e446")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("e06ceed4-f9da-4c12-b48b-a7d6370a314d")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("b7ffd5a5-4022-47e4-aa55-c57e8e07ab68")
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

    @objid ("33b33177-4e35-4524-b783-e04c2394784d")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("a83060f0-4fba-4e2c-a4b6-318fbf3ca804")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("8f061289-5f3b-42f8-be9a-2d333de55694")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
