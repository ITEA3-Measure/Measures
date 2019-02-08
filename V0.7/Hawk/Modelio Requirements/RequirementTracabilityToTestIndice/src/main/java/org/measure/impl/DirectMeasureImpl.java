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

@objid ("f368cfbc-ec0b-4ddb-b925-54376f162f65")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("89e80861-53ae-4c6b-a086-8ecd06d627ff")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("f46829c4-06a4-4e6b-9156-b89f562b99a8")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("3723cc83-4adc-4a1b-a697-1bd7a3b39340")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("d059af84-3754-4236-9a32-2b576cc43608")
    private Hawk.Client client;

    @objid ("a62a2403-e318-4b9f-8829-4e1e29a41f6d")
    private ThriftProtocol clientProtocol;

    @objid ("2349bd54-6a37-484e-b7d8-f7b034a99027")
    private String currentInstance;

    @objid ("8b2737f9-7632-47b0-827e-37b7ae1e73db")
    private String defaultNamespaces;

    @objid ("004cf311-714b-48ee-ac5d-edb86489557c")
    private String serverUrl;

    @objid ("4c7e2bc2-9446-4f7a-aa6b-c10cef196d88")
    private String instanceName;

    @objid ("ab0e3a08-60c1-4f36-b5fd-75b54f0a6aee")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("de2fdc20-572b-424e-be29-4c849c6c5952")
    private String repository;

    @objid ("494e60de-ba82-455b-b927-a011f15bd867")
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

    @objid ("6b73a2ec-7f3c-402d-a6ae-75adbaf75a2c")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        //Number of Requirement        
        //Total number of Requirement defined in the selected scope
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query = "var requirements = Requirement.all;"
           + "if(requirements.notEmpty()){"
           + "var nbdep = 0;" 
           + "for(dp in Dependency.all){"
                   + "if(dp.DependsOn.isKindOf(Requirement)){"
                   + "if(dp.hawkParent.isKindOf(Test)){"               
                       + "nbdep = nbdep+1;"
                   + "}"
                   + "}"
               + "}"               
               + "return nbdep / requirements.size() * 100;"
        + "}"
        + "return 0;";
    }

    @objid ("bf9edff2-24e9-471d-8b97-50aa75b4b19e")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("1e5bc62f-38c8-4f35-b7e2-7cf18139ddd9")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("ff5db24d-e235-4afd-ae6f-6b3a2b76ebf1")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("6b04daa6-759d-4c77-89fe-b5e61937e68f")
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

    @objid ("c3724890-1751-474c-9d35-bad2d13d268c")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("061f1184-fa25-4547-8f9e-6146d6081dcb")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("fc3de2f9-ab39-41ca-8d6c-3ff5f8cca0f4")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
