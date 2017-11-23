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

@objid ("84c36b29-d6dc-4f1b-b581-9396b884f111")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("c803d4fb-c8fb-4b1d-81ed-7b97572343d5")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("e6e03063-fd10-4ca3-8fef-c427c0b3537b")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("57ae0702-445b-4946-9b1a-4ba740490c85")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("bb83d634-2479-4527-8001-9eb22647b17b")
    private Hawk.Client client;

    @objid ("b3b485b0-e079-4bc3-8e0a-a9105c65597f")
    private ThriftProtocol clientProtocol;

    @objid ("bcf1c7ac-6085-4a70-8a2e-3ca8d65f4ca5")
    private String currentInstance;

    @objid ("29d6749e-4429-432a-a48e-c128625272c0")
    private String defaultNamespaces;

    @objid ("ae5badb8-7a14-47dc-9bac-8413f33af668")
    private String serverUrl;

    @objid ("942a1f2b-5e2b-4211-b5ac-1f389c26057e")
    private String instanceName;

    @objid ("f4b908cb-67bd-41b9-abef-e94b25db2c68")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("dac790f9-0952-4844-a0eb-37cc2077f7b1")
    private String repository;

    @objid ("1287c99d-4dff-4109-936c-4c7e6a6aeab5")
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

    @objid ("0884504a-10b0-4c95-b33d-2d7594323609")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query = "var nservices =  Note.all.select(n|'@ComponentEvents'.isSubstringOf(n.Content));"
                + "var result = 0;" + "for(nservice in nservices){"
                + "result = result + nservice.hawkParent.OwnedOperation.size();" + "}" + "return result;";
    }

    @objid ("cafcd410-8749-4a73-abed-7131ba7ec3b0")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("502d3b52-be23-4e56-9796-0bc6a0e7a408")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("0a1ac95c-3599-403b-a9cc-70cadb004a0b")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("f13e6f9e-719b-47db-8511-ae9d02afe7f0")
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

    @objid ("5d4ed33a-2927-41e6-96d7-e4939fe6a928")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("a8390f1e-a10a-4e2b-9c0c-d49cd3ef1f8e")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("88e50edb-3dc6-438e-bfab-c1acead32b06")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
