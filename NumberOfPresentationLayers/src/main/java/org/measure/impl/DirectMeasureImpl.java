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

@objid ("b83df99d-50f0-4f31-98b4-7b746bc93f64")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("26b91611-1379-4bed-971e-9b4dc8e989bb")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("3cbcfbec-f262-4794-ad33-795d8e114fc7")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("851efc3e-2180-432b-9a86-4b35d8ec9eb5")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("6e8fb9ff-2dd2-427c-a94e-ecf149106d74")
    private Hawk.Client client;

    @objid ("71bd5979-ab29-490a-8293-7844593ce5a8")
    private ThriftProtocol clientProtocol;

    @objid ("eefcbb49-186d-4917-a992-da489ffd4797")
    private String currentInstance;

    @objid ("07c884ee-60f8-4b95-ba5b-fe1e41bb5c54")
    private String defaultNamespaces;

    @objid ("f86146db-e4f8-4095-b7e6-00183097198d")
    private String serverUrl;

    @objid ("f14e4ef7-f83b-4eaa-a74e-830ccff17cfe")
    private String instanceName;

    @objid ("b8481b8d-5f41-42ac-9cea-d7acba0d8805")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("894dfab3-f658-4c60-b5c0-f74c07f06b66")
    private String repository;

    @objid ("ee538bdd-0d86-43c6-a6f7-48c02d5a9d8f")
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

    @objid ("cd72e131-970c-4c12-b74d-0e43ff77914c")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@PresentationLayer'.isSubstringOf(n.Content)).size();";
    }

    @objid ("87a70e2d-d18c-4c47-8f8e-a21a3cc47aad")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("9b6e4938-0a7a-4bd1-9da3-5063674968ec")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("4836f9a4-2017-4ff0-8e5f-b57d282fbf68")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("cc3b48e1-2b6d-4b76-bf4c-68786870d67d")
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

    @objid ("8459869d-e79a-4b63-8e1f-f1c2e1178419")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("2cc86dc4-0ca9-4d20-b6d2-27f458960918")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("8aa33fc6-76c4-41ae-851f-40929126d921")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
