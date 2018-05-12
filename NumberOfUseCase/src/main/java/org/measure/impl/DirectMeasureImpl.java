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

@objid ("2376a0db-78b3-40d2-90bd-c1361394c7a0")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("914f3926-9533-447d-b78c-a8ba7d5a516a")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("e80e0a2b-c61a-44b8-9b78-f758c67c09df")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("9ba0e191-5ac3-4ff0-b4f1-ebb0ae678554")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("19065330-9567-43de-85dc-1a7c0f3d892a")
    private Hawk.Client client;

    @objid ("e23f0950-55a5-4e00-a9f0-08983db5299a")
    private ThriftProtocol clientProtocol;

    @objid ("79007b62-781f-4abe-a38b-0033c4a094b4")
    private String currentInstance;

    @objid ("14be1ee0-40af-48a4-ac16-f9d898a5a76b")
    private String defaultNamespaces;

    @objid ("f412dc9d-532a-415a-abb5-ee793b082e88")
    private String serverUrl;

    @objid ("f6089596-b3c7-43cd-99d8-e11d906c6815")
    private String instanceName;

    @objid ("63a7695e-2bfd-43f9-a040-41654de61b41")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("17bb0ee2-2a6b-4421-9c82-a445b1444e2d")
    private String repository;

    @objid ("12d8a149-6f4e-47ec-a0cd-4a539387417a")
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

    @objid ("05133ba7-a8dd-4cca-9a0f-e3cb3f2a6e83")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        
        this.query ="return UseCase.all.size;";
    }

    @objid ("cc46ac40-bbde-45fc-94ba-bd63ac5af993")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("29fce635-436d-4570-a376-cf04538c9527")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("cd77640f-4fd4-4585-bd4a-771af28c2d75")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("0f21b73b-e035-45c6-8488-715b16b2cf6f")
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

    @objid ("71d346b8-3fe9-4370-bc7a-43da0756b9ef")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("291bb37d-835c-406c-9ffd-3101493834ac")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("bb165b86-6c02-4837-bd8c-bc7ec977ea59")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
