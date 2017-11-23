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

@objid ("c87825dd-f425-498d-a6d4-4b6f6f962308")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("ad196ba6-1d2d-4212-859a-4568e9dad059")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("e677e85a-dd43-4c70-b386-33c7d894aa08")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("bec8547a-1447-442e-8ab3-1cef4d87ae99")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("b557d3de-a82b-48ff-b455-d9843ffb22d8")
    private Hawk.Client client;

    @objid ("90776a4d-b020-4209-bae1-03f0a19e7b6b")
    private ThriftProtocol clientProtocol;

    @objid ("88f45860-df35-4e1e-9ab1-32ab401c2c72")
    private String currentInstance;

    @objid ("877c2722-2e39-4f46-9e41-405957316209")
    private String defaultNamespaces;

    @objid ("36df3971-ffbc-435c-a3dc-f4a1935c6387")
    private String serverUrl;

    @objid ("6fb1bb38-b995-45e6-8148-1a67e63ff6c3")
    private String instanceName;

    @objid ("b4a7c234-9896-404b-9660-dee3598c3c26")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("d400dda4-7f12-42fa-b5ce-4e4cc423d666")
    private String repository;

    @objid ("27990088-47a1-45c2-8378-85648d5fe32c")
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

    @objid ("6fe452fe-3d60-4b9a-8c56-929450f88623")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        
        this.query ="return UseCase.all.size;";
    }

    @objid ("6407b265-93e9-4f32-a39b-f34e7e48f934")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("7c41cd72-bf63-4f3d-8838-2cffcfd4462d")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("e385f92c-d419-4477-87bc-7c90c5d9c6c1")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("32cabfd3-723f-4daf-b400-975ec4698d34")
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

    @objid ("8bc366d6-6ead-40b5-a719-5f8ab05b2f9e")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("b1aeb61f-31f4-4916-acd0-694f17c41bf0")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("b13fe647-0693-4e1a-b281-19afba8dedb3")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
