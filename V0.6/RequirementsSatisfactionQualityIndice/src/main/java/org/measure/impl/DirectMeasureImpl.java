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

@objid ("b3142a96-a36f-4327-a439-ea8f4ccc1174")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("a824a6cc-2836-428e-865c-62944a345df8")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("201f640b-5309-4320-94ea-2968251bab3d")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("eac91856-800a-4583-bb91-ff18eb54a624")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("dcdac0fc-2199-42d3-8fb2-9855f27825e5")
    private Hawk.Client client;

    @objid ("422c90bf-d0d7-47e0-bcb6-511f0ca240aa")
    private ThriftProtocol clientProtocol;

    @objid ("1be45b7f-41e4-407e-bbe6-421352b7be84")
    private String currentInstance;

    @objid ("5da99b7f-fe69-43e9-a47c-55b69bfdf019")
    private String defaultNamespaces;

    @objid ("4a50b133-04e0-47d7-9ebd-a35e75c418de")
    private String serverUrl;

    @objid ("8b422f37-fff9-4fc7-8a47-205b4005022f")
    private String instanceName;

    @objid ("abe5075d-a2b2-4fe2-87f9-6115a83c9578")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("93b0cffe-a918-4bf7-856d-15f1f7205304")
    private String repository;

    @objid ("d7be6610-dc56-47e9-b8e0-a59fbf503925")
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

    @objid ("d18cb1ff-0620-44ca-bda2-a142249acedf")
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
                   + "if(dp.hawkParent.isKindOf(Test) == false){"               
                       + "nbdep = nbdep+1;"
                   + "}"
                   + "}"
               + "}"               
               + "return nbdep / requirements.size() * 100;"
        + "}"
        + "return 0;";
    }

    @objid ("545f1f3f-eb8b-4830-ad0c-c62138403df2")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("dba59f9a-ac0f-4e29-b43e-9034b948af68")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("1ba3bce4-3bb1-4138-a0a7-b982639bb894")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("f36de2f0-a5db-4d53-a169-d8004b42897e")
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

    @objid ("c800411c-59ff-4a81-8267-f64c635a1aeb")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("99b8c1e9-2cbb-4c2f-af8e-5c3b309b325f")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("74b4291d-a130-4fd5-9bdd-1390fc49755f")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
