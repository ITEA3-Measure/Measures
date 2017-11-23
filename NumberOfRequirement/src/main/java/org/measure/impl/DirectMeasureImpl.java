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

@objid ("4f451456-ddcb-4f34-84f7-4ee891fe9d35")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("1d5fc19b-70fd-4fa3-8783-39f5c6959526")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("ea097aed-ae58-4460-a750-d1ef9cc8eb8b")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("d42fbdba-e8b5-4eb8-b954-6183bb637a00")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("b5dc9eaa-52cf-4f82-93ed-96becee160ed")
    private Hawk.Client client;

    @objid ("bd05e447-59c6-4208-bc45-3bc12cc96d3d")
    private ThriftProtocol clientProtocol;

    @objid ("03cfa1f6-39a9-41a2-82f0-7c5c561dd84d")
    private String currentInstance;

    @objid ("96d837c5-b15f-4949-8a76-07db3da7546b")
    private String defaultNamespaces;

    @objid ("d74ef88a-4ecc-41a9-8451-c4d20ef82be3")
    private String serverUrl;

    @objid ("cf6a9e71-bdad-4631-8724-4febc7cb04fd")
    private String instanceName;

    @objid ("e4563e13-f51b-40fe-b51b-efcd2221e2f9")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("ada5b420-198a-4a75-8a44-6a7362ca1c84")
    private String repository;

    @objid ("0c3785b2-dbe1-40f1-a900-495f3a8498ed")
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

    @objid ("dd471bf2-f5d7-412c-989d-0f58b3d02a7f")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        //Number of Requirement        
        //Total number of Requirement defined in the selected scope
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query ="return Requirement.all.size;";
    }

    @objid ("7a60de0c-7d9d-4ba9-b6e8-0a4b0b5189e5")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("e2afe295-1aeb-4afa-b3b5-7f509809f637")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("cc0a5736-aebf-4601-a8e7-29f3354604c8")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("76131d18-162f-41f0-95ab-e9cec5cda4df")
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

    @objid ("af3dd2d5-1f58-48ce-bbd8-4dad58be4522")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("7a5b1c29-4ab5-45b3-b982-8dc375e4038a")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("cc3fd7cf-67b4-4e31-a2e6-fd5081ef62ce")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
