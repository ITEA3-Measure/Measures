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

@objid ("e34f9d32-168f-4a8c-ab5d-25466cf5699b")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("6d09d3a9-ade2-4b42-bbe1-4ed41144dcdb")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("34db2270-860e-4bd7-a14f-2751a27a6e79")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("6a1cb28f-2ede-47a7-8a08-2425bf26b5ec")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("95923a1a-f1df-4344-9f60-7d568cb3e41e")
    private Hawk.Client client;

    @objid ("bb75d984-eeb1-4563-8efe-867537b598d5")
    private ThriftProtocol clientProtocol;

    @objid ("1843c4db-6a88-4e8a-8ead-866d410aca79")
    private String currentInstance;

    @objid ("f17be19f-826f-43d1-a495-ae194270fb94")
    private String defaultNamespaces;

    @objid ("20cdfb63-f3a5-4926-a818-4da314370732")
    private String serverUrl;

    @objid ("a390c94b-a064-441c-8d22-8028837dc6b9")
    private String instanceName;

    @objid ("ea4e9fa9-e898-4c43-81be-f7be75ef894b")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("64bfc0f8-0a3c-4d7d-824d-bf6c149a346c")
    private String repository;

    @objid ("1ab3a069-a3ad-4518-b9de-7a63cc932340")
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
        
            // send query
            QueryResult qResult = executeQuery();
            
            IntegerMeasurement res = new IntegerMeasurement();
            if (qResult.isSetVInteger()) {
                res.setValue(qResult.getVInteger());
            } else if (qResult.isSetVDouble()) {
                res.setValue(new Double(qResult.getVDouble()).intValue());
            }
            
            result.add(res);
            
            // disconnect
            disconnect();
        
        } catch (Throwable e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
        return result;
    }

    @objid ("c5b89639-0ff4-4146-9375-875d01a8e5eb")
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
                   + "if(dp.hawkParent.isKindOf(Class)){"               
                       + "nbdep = nbdep+1;"
                   + "}"
                   + "}"
               + "}"               
               + "return nbdep / requirements.size() * 100;"
        + "}"
        + "return 0;";
    }

    @objid ("b18c08d8-3085-43c8-a799-4cc769dcb4c3")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("c739fc7e-76ef-4939-a790-cdd812657066")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("7eb83f3f-7a98-45af-8257-4221fa76732b")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("b5cf94fc-4144-48f4-b878-8b518f72bd75")
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

    @objid ("58786a81-0bf8-4268-8752-7a241b923fed")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("9e6ff3fc-1903-4226-ac3b-330497fb1773")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("c95990d5-b0c8-40e4-8a7d-6825d3d7bebe")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
