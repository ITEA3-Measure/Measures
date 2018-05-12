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

@objid ("56877440-b00e-4e15-ba43-3f1d5ba8ff6d")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("a1471c67-56bb-4980-9da4-cb838be922c8")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("52f0acbe-32c7-4a16-8cc4-a4c4de9bdf85")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("fb2adeed-eefa-447a-8f85-80d496d1550d")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("d4490808-f9e8-4151-8f92-d3e53d992a64")
    private Hawk.Client client;

    @objid ("2073266f-52c4-41a8-af1e-0d234edb1dbe")
    private ThriftProtocol clientProtocol;

    @objid ("b3fa0ec6-d7c4-4ed3-8915-5965ef8eec5c")
    private String currentInstance;

    @objid ("116d496d-5c41-4438-b739-5188bfee972b")
    private String defaultNamespaces;

    @objid ("a897e21a-6e45-4ea0-9ada-090a63affe8a")
    private String serverUrl;

    @objid ("481b0bf3-5f71-4bbc-89f3-b2a4c61fa957")
    private String instanceName;

    @objid ("c8a4954a-93a0-48a4-b00f-182711822a6d")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("f05eff98-5e9a-4217-b1ad-c440a4267c6e")
    private String repository;

    @objid ("a1d952b9-57fd-4d2d-895a-a0f19ac1c9f7")
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

    @objid ("48dde76c-cecb-4851-9059-4422557f91e0")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="var nservices =  Note.all.select(n|'@ComponentServices'.isSubstringOf(n.Content));"
        + "var result = 0;"
        + "for(nservice in nservices){"
        + "result = result + nservice.hawkParent.OwnedOperation.size();"
        + "}"
        + "return result;";
    }

    @objid ("babd4f9a-94d8-4d00-8151-f6b0da448a4a")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("0c64d959-c101-4d1b-b185-9a9d5c5a5092")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("369dc5bc-6615-4166-8f3f-3179b02c373e")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("aa6d0705-850e-4334-870a-6c187607cee2")
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

    @objid ("4ff1ec8c-a66c-4042-9443-7645e6fcdd31")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("de586913-f31b-48ce-afaa-fdadfc191611")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("f5e9d579-3ce1-460f-8352-266df9ffc651")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
