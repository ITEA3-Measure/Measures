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

@objid ("d98fa526-9357-4f86-84c0-03f1df0e9d61")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("e95c94b5-c0df-44a3-9e28-9d628f3413f6")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("623827b0-bba5-40aa-a03e-deb175c28e8f")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("5b3cd4b2-3754-4dde-9cf7-0fbd1590b64e")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("f1b2d48a-2c49-4376-8be7-344e0d6a4997")
    private Hawk.Client client;

    @objid ("4125388f-9a58-4f44-8b8e-785dfefc14ab")
    private ThriftProtocol clientProtocol;

    @objid ("1bf24d21-2a60-43d4-aa17-05dbc4cb3200")
    private String currentInstance;

    @objid ("2aed1d07-8f71-4de0-bcbf-9db27aeeed82")
    private String defaultNamespaces;

    @objid ("3117600c-3c9c-421c-a5c2-db8137479250")
    private String serverUrl;

    @objid ("8ab48853-1e6f-421d-926d-c1de475a0e2e")
    private String instanceName;

    @objid ("ae99ef4a-9762-481a-8b89-d336d445f016")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("d14b94f5-d228-49b0-975c-bb13cafcf4b5")
    private String repository;

    @objid ("efab0bb0-e477-4717-ad1c-b087219b7b06")
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

    @objid ("3f22bd33-0aaa-4390-9a97-3a48c67bf29b")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@ComponentEvents'.isSubstringOf(n.Content)).size();";
    }

    @objid ("3cb0cf1a-1858-4aeb-a9a5-d1201e32b73f")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("dcd6f58b-4042-4b62-9c81-b6fdcb502143")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("093bf22d-0485-48be-b8ea-42ea99f5e28f")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("7b77d787-4c28-447a-8a35-b37ae3979248")
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

    @objid ("a728b3e3-4bba-403f-bb0f-7ab295b705a5")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("a40e666d-6d4a-47f2-b63b-3ae3f1d7974f")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("884906cf-29ad-4cf9-ad89-fa2b62bc7a35")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
