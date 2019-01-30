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

@objid ("6acc72b5-5ff5-4304-8028-4356789d419c")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("5322d68e-a2b6-4dec-9a44-567d4e0a2300")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("0e2d8d26-8e02-444a-873d-283e9d1a62ed")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("0b6f5f5d-a412-47be-a020-c73803b42eb7")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("1871c435-e192-4ad4-956a-0c858c50d65a")
    private Hawk.Client client;

    @objid ("aee77278-47c5-4042-8b63-88c61ef33ef6")
    private ThriftProtocol clientProtocol;

    @objid ("2c16507b-57d5-4cbe-9b50-6f43849ca14e")
    private String currentInstance;

    @objid ("e8bd50c8-0414-4fb0-a6b8-385a8c308e42")
    private String defaultNamespaces;

    @objid ("92be620c-4dee-46fa-9ea7-ff271c997e5f")
    private String serverUrl;

    @objid ("0913c24e-19cc-4570-8459-f2e23dc5042b")
    private String instanceName;

    @objid ("95668802-86c1-48d0-8b75-f50f95148b91")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("92a527d2-e7a9-4bea-956b-7c175dff0be6")
    private String repository;

    @objid ("ce990e10-fdf2-47aa-b69d-baffcf7802a2")
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

    @objid ("97eb7eb4-c0b0-48b7-a716-273cf5347eba")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Component.all.size();";
    }

    @objid ("eaae9e13-2e6b-43c4-b3d6-14c1dbc19e7e")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("8579ea68-760d-43a9-81bc-38041ed6f74b")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("8782fe2a-3a77-4252-9147-3071bc2c95bb")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("9ef5fa49-9085-4574-b015-41690b7d1ff9")
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

    @objid ("2db8b72c-8d0a-4002-92e4-754cfbdbc714")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("bbcf9423-9708-473c-9e64-60a25b2edb8f")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("e790f411-0439-4785-84e3-76881c27ce79")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
