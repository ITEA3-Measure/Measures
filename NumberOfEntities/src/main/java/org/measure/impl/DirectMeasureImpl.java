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

@objid ("b35c1d14-fe03-463f-a6f6-bc9ac3577a34")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("bfa96d98-0a44-486f-8fef-f494d79d595f")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("1336005d-df90-4e31-8176-58601c89b066")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("ee685477-14ac-426e-873e-6ce88e5ddf96")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("f4843239-95bb-4836-97ee-cc19bfde3174")
    private Hawk.Client client;

    @objid ("fccb3b59-b36b-4e62-a3bf-d5dac48597da")
    private ThriftProtocol clientProtocol;

    @objid ("8565eafb-fed5-429f-ace7-2ac59c9d7766")
    private String currentInstance;

    @objid ("a5730f37-778f-4733-b6ff-0b0e7f56ec5e")
    private String defaultNamespaces;

    @objid ("c04848cb-92f6-44a4-8c42-aea6de35f0e4")
    private String serverUrl;

    @objid ("52aa6216-7887-4cc6-909f-dad4b4e0334b")
    private String instanceName;

    @objid ("803d4644-d4e0-4533-a107-077a3426553f")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("548f3184-32d0-47ce-827e-72b11585668f")
    private String repository;

    @objid ("c64e5159-a969-4f89-abf4-55403bb89e7d")
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

    @objid ("b1f17d67-719e-4f45-adcf-0788f79cff3b")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@Entity'.isSubstringOf(n.Content)).size();";
    }

    @objid ("075bd1bb-f053-4733-8a13-76871c60dad9")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("4cc428a2-b2ff-4be0-a883-d5e909fdbb7a")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("3489486f-9873-4333-b8ed-bbcdf4116bc3")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("2b4388fc-8990-415a-a005-4ac006113cc4")
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

    @objid ("1dbc2e00-ac41-4dac-8f53-8f54642cb7cc")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("336ed746-28e7-4737-ac42-e4ac6facc613")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("37907e88-bd2e-4ac6-adf7-a0101141dbbf")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
