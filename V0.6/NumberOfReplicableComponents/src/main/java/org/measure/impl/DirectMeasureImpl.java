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

@objid ("25ef30b0-403f-4ca9-8200-08e74a044c80")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("3928000b-10ab-4da2-a259-d2c745859ff4")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("c34bbeba-863b-425d-8203-bf29d13e50ca")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("a285f76b-5e00-4efa-8616-c253951b2fc1")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("16c6c842-024c-4708-9683-c031d258f1c0")
    private Hawk.Client client;

    @objid ("9272ea6f-dcf1-4db5-9d5f-f64e9bda2886")
    private ThriftProtocol clientProtocol;

    @objid ("0bc0f6dc-3bfe-4f9c-abb5-65040372553c")
    private String currentInstance;

    @objid ("73c60f13-28b2-443f-aa16-b3b29159bbe3")
    private String defaultNamespaces;

    @objid ("30833c6d-164d-4767-9998-1dbc11ad67c9")
    private String serverUrl;

    @objid ("844f9a02-d06e-412b-ad8e-e0e3b65a55d4")
    private String instanceName;

    @objid ("9c5730d6-157d-4182-a637-dfd1ff5466cb")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("37835612-bb6a-4fa3-99b0-ce95a4f50efb")
    private String repository;

    @objid ("93975517-5a83-441b-a82d-00970a9bc04a")
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

    @objid ("146096c0-ffe0-4d26-9cd4-c38f883f562f")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Replicable'.isSubstringOf(n.Content)).size();";
    }

    @objid ("1e9dfbe1-aeb8-4098-be30-364bf63fbf2f")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("26691d6d-163c-4ae6-ad90-8fbbee4d52ae")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("2bcc6817-cce1-4735-8bd8-5eaa02134988")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("ef1a06ce-df2f-4724-b4e0-1ab5593cd248")
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

    @objid ("910f380a-90a0-4b0c-85a8-403829d5deb4")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("446b80a9-fd32-4c97-b7e2-924132db66b0")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("11a7ea94-7285-457f-b284-de4815a59e73")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
