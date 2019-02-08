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

@objid ("1afdf92b-a1da-4066-8d70-df4eb478a400")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("f918fb52-d2bf-4480-9055-e95ad9f80844")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("f386eeac-fa39-49e8-ac5b-8585ad664074")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("744f447f-f8f7-4fa0-bc54-7787c9f3348d")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("82e3dd78-06f0-4d9c-946d-01db6e182d4d")
    private Hawk.Client client;

    @objid ("339bdfa7-5020-4c34-9312-aa2bc8658512")
    private ThriftProtocol clientProtocol;

    @objid ("b8d727cc-4b27-4923-ba1e-9d6212c8f8b0")
    private String currentInstance;

    @objid ("2f8714a5-1dfe-4df0-9f4c-8f838d3b7e25")
    private String defaultNamespaces;

    @objid ("f254784c-97e5-48d1-bf07-5b9427d2c60a")
    private String serverUrl;

    @objid ("fce8968e-a959-4081-8514-ac52bea2496c")
    private String instanceName;

    @objid ("40dd68cd-bbb4-44b5-8c4c-4c8197c98d0e")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("d26b5709-cadc-4d32-a470-3586e786f857")
    private String repository;

    @objid ("370a2425-e40e-4206-a2fc-9e260b4cf390")
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

    @objid ("d9e19a93-f34c-4bd7-a241-44c2f518b0ea")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@Transactional'.isSubstringOf(n.Content)).size();";
    }

    @objid ("879c7c0d-ab9f-4858-aa7b-c3d873e04e77")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("53e828f1-6704-4245-948d-9f056960aad8")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("4ae58229-1eb2-4685-8ad7-4112dfb32461")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("e85c8155-a652-4b16-a38b-07e1cdd6fc70")
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

    @objid ("833bbf4d-dbeb-4425-ad61-b09996298e2a")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("1807adff-1755-4362-bf9f-034026716cf7")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("4d802998-f375-46b6-b598-6a3ad8472ac5")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
