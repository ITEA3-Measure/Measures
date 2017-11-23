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

@objid ("3f04ba8c-92c4-461e-96ea-c09efcf3b50f")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("58e678d6-1ffc-4732-b2b3-207a5e3d2da7")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("8165dc1b-6fe9-4b1d-a77a-189f8b43cf64")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("be22a674-28e6-437d-aa15-5eaabcf6f7ef")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("139ab9c2-478d-4563-8d93-ec2cd859c408")
    private Hawk.Client client;

    @objid ("dea9a453-cf31-4082-9399-6feacc0747b5")
    private ThriftProtocol clientProtocol;

    @objid ("aabf130e-809a-43e1-99f3-ca84936811f5")
    private String currentInstance;

    @objid ("fcb0f89b-8b8c-4706-9d16-dbd406da35db")
    private String defaultNamespaces;

    @objid ("f245aa38-0b1c-4809-b23c-7f29b349a20b")
    private String serverUrl;

    @objid ("0093cb89-bae3-4b3c-a85f-f7faac62211d")
    private String instanceName;

    @objid ("76e7c353-ee72-4793-ba09-d4212eadfc9f")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("ab7d92c6-e9b3-4652-874a-4a0d54bf1722")
    private String repository;

    @objid ("7a23afe5-013b-4334-ac78-a2fc26e16a5b")
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

    @objid ("e5b7364f-e7bf-48d0-bf96-b06a3dd90223")
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

    @objid ("362943db-4c62-454b-ab9d-2ae6e3f3fce5")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("39cbef28-e5dc-443e-8bf9-d3d26bec2586")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("a0bc6324-4c92-4374-a024-77951c542519")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("b310e019-dd42-4f96-9c7f-98061ebf6c23")
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

    @objid ("ee16f891-5541-4a50-8487-c3e21cc5a10d")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("74556f13-4f5a-45cd-8a25-cc73005c1c22")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("49285683-b540-433d-ba7f-388fad471080")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
