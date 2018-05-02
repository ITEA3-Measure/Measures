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

@objid ("d4204e36-91c2-47a4-980e-dd039bbbb755")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("8aeefbf0-376b-4abf-bbf3-89b1dd118536")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("9c151850-298e-4f0e-a895-1d0a995a9ea6")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("9aa7bb8f-cbda-4514-9c4a-dfbdde53988a")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("a28cd075-461a-468a-82ff-9f16b7ee1dd6")
    private Hawk.Client client;

    @objid ("da8b793b-6390-432a-97d7-0ce5b71cf7be")
    private ThriftProtocol clientProtocol;

    @objid ("11d05f34-6767-409f-8e58-0babde5ddad2")
    private String currentInstance;

    @objid ("575049cb-740b-4bec-a50f-75707720bdaa")
    private String defaultNamespaces;

    @objid ("3ba725ea-26e3-4fc6-ae5e-afdf57885e5a")
    private String serverUrl;

    @objid ("60ba7f71-c4f4-4da2-b1f6-5c1017674eac")
    private String instanceName;

    @objid ("3c3c7965-0a9f-44e5-a9cb-66ecdbb75d7f")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("23bbda56-7f4c-4594-9f68-a133e645d29a")
    private String repository;

    @objid ("102313b2-15eb-4a84-a9b8-4604d0533121")
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

    @objid ("aec0bafa-1ce9-4d16-8a57-2075282600da")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@ComponentImpl'.isSubstringOf(n.Content)).size();";
    }

    @objid ("1e3c348b-33a4-461f-a0ce-05a14ab077e3")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("982b4254-af2a-4b8d-84b2-469d91c0a10f")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("48e45ffe-b5d1-4bfc-9488-9e48d479f240")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("e6dcf79c-04d1-4100-9190-fea8568c1480")
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

    @objid ("9e46cf3b-d283-482a-b916-2a298af66d66")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("6afafc11-cec3-420d-b823-dd1623000ddf")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("b0181f7d-ef3c-48e9-8e1d-163c349c27dc")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
