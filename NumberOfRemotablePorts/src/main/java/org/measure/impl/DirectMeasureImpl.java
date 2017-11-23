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

@objid ("c5f52e32-ac48-40a2-9e7a-d6b202fb47cf")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("df19113a-1484-463b-bc92-21f06afe2718")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("b21bea4f-2bbf-49de-b4e4-04e232dcda80")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("0bcaebfc-60f1-4cf5-a5f4-ef9c756339ae")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("318822c5-02d7-4725-ae01-004523a35976")
    private Hawk.Client client;

    @objid ("6e7ecd32-a45b-4662-b9b0-00db1738ba79")
    private ThriftProtocol clientProtocol;

    @objid ("162f5961-910c-4703-870d-d3808f819f8a")
    private String currentInstance;

    @objid ("50bfd386-4e49-4ec5-aed1-4d3c28e2a2ee")
    private String defaultNamespaces;

    @objid ("4d15c946-4ec8-4ebe-b93a-c2bf6ebef81d")
    private String serverUrl;

    @objid ("41aa02b6-2c10-4f25-8a8e-8c70769a41ee")
    private String instanceName;

    @objid ("ae432e00-e102-4519-bcd3-1933bd868dd3")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("55a17260-aee3-401d-9189-b8fd5f0f672d")
    private String repository;

    @objid ("3f55281d-663e-457e-abac-7bd7c26ba49c")
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

    @objid ("39d95745-e93c-4765-826b-5b791753bb91")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Remotable'.isSubstringOf(n.Content)).size();";
    }

    @objid ("bd5672a3-579e-4cc9-9172-3903496ef8e3")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("1f11c6b7-ca75-449b-b0a6-c16211371af0")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("ac08b240-8941-4f16-ac7c-9892497fc9a4")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("6922f352-0a09-4861-bcdb-6493c1d8ff7f")
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

    @objid ("e2b4655e-4543-4f44-90be-448fe3747218")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("971f01ee-a92c-4f3e-b210-b37a8785b852")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("61957bb5-2fdc-4f21-b16c-f58c1897b43f")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
