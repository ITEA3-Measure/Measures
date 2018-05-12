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

@objid ("54eba6c7-39d4-4565-8a3c-68d026992d73")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("bf8fb873-f492-4df2-8f49-678e427fdd30")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("51dd3791-f89d-43c7-9958-7f6ec089b0c2")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("11eaaa11-d30c-405e-a68e-5fa9c276e93f")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("e09c953d-e71d-486b-9bd0-23e78ec0b51d")
    private Hawk.Client client;

    @objid ("f8fa8ac9-2347-4e3a-a1b3-88c2e7abf20b")
    private ThriftProtocol clientProtocol;

    @objid ("07ba9750-7b22-4ad8-abf1-dbd15993d20e")
    private String currentInstance;

    @objid ("bc7ed9b8-9314-47c5-b400-9d653ed1c3a5")
    private String defaultNamespaces;

    @objid ("64e42a44-c368-4648-b23d-de02e5e39f03")
    private String serverUrl;

    @objid ("071bc54d-6d67-4d65-afe3-62aacc1c6dd0")
    private String instanceName;

    @objid ("8f837207-4ac6-4d27-a161-38bf7d82e952")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("317169d8-02b7-44b5-8892-2eff3702cad9")
    private String repository;

    @objid ("0f8ed871-e1a4-4125-b6bd-8ea8382c53c3")
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

    @objid ("d4e50a3a-85fb-4249-a492-94bc1d291723")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@LogicLayer'.isSubstringOf(n.Content)).size();";
    }

    @objid ("7ef25d94-e5a9-4360-9e61-d41f56f7ac36")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("cd58fd1e-21b2-4587-8af9-02a87aff4410")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("72da4c77-e2da-48b7-8a33-eda6fcf6fad6")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("229377da-6b2a-4701-8ed3-335e43362992")
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

    @objid ("7567d80d-0a13-4d05-aaf5-fb2cb9a4cd8d")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("ae720ae7-8d7f-43cb-9215-4dbef450092e")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("381d4f1f-424a-4419-9e9a-b9eab49d4bc1")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
