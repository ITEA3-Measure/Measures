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

@objid ("010d09ef-8ff3-4e37-a41f-2793ff1dee8c")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("1fae617a-464a-4508-9fe7-0993cbd63e58")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("a1cbb83a-ea58-4eec-a613-4785d61c5915")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("29999cff-aa3e-44d8-8f24-101f3914dbb2")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("1ad71c63-d378-418d-9267-0a5e6819d062")
    private Hawk.Client client;

    @objid ("9cc56f26-f1c2-44bc-b61b-e3c4c7b9fc03")
    private ThriftProtocol clientProtocol;

    @objid ("d6ae0017-fa1f-43b0-8ef5-34d5b5535e1c")
    private String currentInstance;

    @objid ("713c5fc0-97eb-4630-a659-34b437926514")
    private String defaultNamespaces;

    @objid ("a169b184-09b0-4f4f-b526-b360ef0f7493")
    private String serverUrl;

    @objid ("dc26e41d-84ae-4815-87ea-e5ec6cf1aee2")
    private String instanceName;

    @objid ("2c1b4ba4-f3c0-4712-93c5-470ff01b4154")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("1774de8e-d370-4ea1-b416-e85a3fb93549")
    private String repository;

    @objid ("d31c63ad-55e8-44c5-879e-3b073ec4d72c")
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

    @objid ("0673520c-91f9-43a8-85fe-8eb2f8acf709")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        //Number of Requirement        
        //Total number of Requirement defined in the selected scope
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query ="return Requirement.all.size;";
    }

    @objid ("78f5b5ad-5f3d-46da-a3f7-1d7ca7fe6b62")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("7202a3bf-55ed-4d37-93a9-24330a936958")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("2ed63be5-7ae7-4874-98ad-513fad51055f")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("8a2248fc-2bd8-4735-b906-173579b0549c")
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

    @objid ("90153779-5b88-4166-a5fd-758d146b2ce2")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("d1c40287-9b4c-4865-9c6a-b45ce494d316")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("38385579-abf9-4551-bb85-c424340a6cbe")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
