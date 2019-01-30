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

@objid ("9e2b607b-32d5-46d3-a82d-7d6372826d19")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("fd71e846-1667-4d7e-a47a-d727a9f2a930")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("210aebfb-bfe0-4e3d-84bb-d454d3e7b72c")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("c5f0f54b-84e8-4fbf-935d-3867e1ff4bb8")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("8a5a8a7f-2aca-40f9-842c-5d0b28f24517")
    private Hawk.Client client;

    @objid ("c271cc5d-9dd7-4999-90fd-a76c82511f2a")
    private ThriftProtocol clientProtocol;

    @objid ("5df87dc5-d823-4182-a5e0-3e923451d21a")
    private String currentInstance;

    @objid ("05228fa7-4e7e-43cd-a2e1-73748fd1e558")
    private String defaultNamespaces;

    @objid ("6a340002-5c30-46f8-b1d1-0d28e1ebbe85")
    private String serverUrl;

    @objid ("1de7f4f0-dbdd-4c70-845d-b32bf1d4f096")
    private String instanceName;

    @objid ("d7a07fd2-4676-4708-aa8c-01cc4c7e9fc4")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("b7310c35-058b-42b6-afb0-c462609896df")
    private String repository;

    @objid ("3f08d307-33af-4c00-9fb2-2738e8b8d215")
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

    @objid ("b80414d0-fdf0-4515-aba3-729d5f6146b1")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Component.all.size;";
    }

    @objid ("e8113fb2-943e-4b11-9bf4-eca9bb0a4a5f")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("ff406d8d-59c6-4071-92d7-b8bb885a25cf")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("d97bdb93-ae51-454f-9422-2ff16648ffdf")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("9a731a96-1efb-49a0-874d-898a9c4c1781")
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

    @objid ("d8771d9a-8b30-4349-8b66-14ef92d30fdd")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("e273c04d-21fc-42e5-bbb9-fa7fa93e635a")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("ad6ce396-3975-4705-9b21-e7ef5389a04e")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
