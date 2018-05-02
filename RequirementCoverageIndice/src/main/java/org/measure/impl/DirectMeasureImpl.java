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

@objid ("41b7a485-204a-4cd4-aff3-ffd2bcc01d8e")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("e5c6df86-1a6f-4f06-b08c-efd6678bf9ed")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("086d1e0f-573c-4830-90da-6e495acbe01b")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("d811f05c-5bc7-4585-86e6-6b2f8ddb7d88")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("9bac62b6-0694-4858-9164-55be2d4bd855")
    private Hawk.Client client;

    @objid ("ae835556-a007-4375-8743-1886a036fe83")
    private ThriftProtocol clientProtocol;

    @objid ("49dd67d3-3e4e-44ce-bdec-08fa379de2e5")
    private String currentInstance;

    @objid ("8cb6352a-6211-48b4-838d-99debcbdf64e")
    private String defaultNamespaces;

    @objid ("93b605af-8f60-40e3-8b61-2a0a8b864810")
    private String serverUrl;

    @objid ("1b44e1ff-6249-4fab-8582-c654f530793c")
    private String instanceName;

    @objid ("ba789380-4261-46e2-9bcc-942dab77f82b")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("5882f55d-f066-4f4e-ba0e-811292902904")
    private String repository;

    @objid ("49304b49-81b9-47db-9aa9-e0a7e88cf12e")
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

    @objid ("fc45317f-9407-412e-95c2-1891e7af62ab")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query = "var requirements = Requirement.all;"
           + "if(requirements.notEmpty()){"
           + "var nbdep = 0;" 
           + "for(dp in Dependency.all){"
                   + "if(dp.DependsOn.isKindOf(Requirement)){"
                   + "if(dp.hawkParent.isKindOf(Component)){"               
                       + "nbdep = nbdep+1;"
                   + "}"
                   + "}"
               + "}"               
               + "return nbdep / requirements.size() * 100;"
        + "}"
        + "return 0;";
    }

    @objid ("5fe73984-4cf5-4d19-a5c0-ef9ec067d3c4")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("74fd3eb9-e01d-4447-8b3a-a64342b8ca46")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("a567840f-36ca-4789-9ee3-c980df0aa0bd")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("ff07472a-9e8c-4ee3-a79b-f35183b6457a")
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

    @objid ("2fdaf66e-d6d7-49d3-84cb-2d5c78ad59ae")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("53bdc268-0d4c-4a7d-aa51-ec109a4dcac2")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("77f41ea0-1b41-4ac0-87b1-a0f13736b55c")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
