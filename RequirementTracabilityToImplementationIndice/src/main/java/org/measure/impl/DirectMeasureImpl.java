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

@objid ("d841a7a6-1099-4529-84df-d49519bb0710")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("eed3f9d1-f4eb-48b3-a87a-83faefb165b0")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("70ee1c92-a712-460a-93c2-648327b6866e")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("fab8fa30-ab5f-4a89-b6d2-98462c17e4c0")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("a5129cc3-5777-4629-bab7-7ccd1d8a870c")
    private Hawk.Client client;

    @objid ("9b43a826-4e93-4c51-96ea-3ae8c6015cc2")
    private ThriftProtocol clientProtocol;

    @objid ("c1d85438-bf6c-4564-8813-157bc7a14376")
    private String currentInstance;

    @objid ("7204d6f7-dd39-432e-a3ea-abfd8b8942c5")
    private String defaultNamespaces;

    @objid ("f0cca66b-b646-4ec6-9811-413303cbef68")
    private String serverUrl;

    @objid ("42cb3577-d29b-4b51-8bc1-e8b07f33caf5")
    private String instanceName;

    @objid ("79031842-bdca-4f9d-8e0f-8b6686c70644")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("ff76a417-e0d1-4a19-af2d-6fc907c0aa7e")
    private String repository;

    @objid ("65d18dd8-2464-4459-9fe0-9ac61c1e2a8d")
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
                QueryResult qResult = executeQuery();
        
                IntegerMeasurement res = new IntegerMeasurement();
                if (qResult.isSetVInteger()) {
                    res.setValue(qResult.getVInteger());
                } else if (qResult.isSetVDouble()) {
                    res.setValue(new Double(qResult.getVDouble()).intValue());
                }
        
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

    @objid ("ce200667-c95c-4a4b-b400-fdc6ca09b7eb")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        
        // Number of Requirement
        // Total number of Requirement defined in the selected scope
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query = "var requirements = Requirement.all;" + "if(requirements.notEmpty()){" + "var nbdep = 0;" + "for(dp in Dependency.all){" + "if(dp.DependsOn.isKindOf(Requirement)){" + "if(dp.hawkParent.isKindOf(Class)){" + "nbdep = nbdep+1;" + "}" + "}" + "}"
                + "return nbdep / requirements.size() * 100;" + "}" + "return 0;";
    }

    @objid ("1d899837-d31f-4f5a-be04-4ddbe22b02c1")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("05072013-fa63-4ebe-872c-08f1d7f8a073")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("29612fdf-f88e-4ff9-917b-4387adee7e99")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("4f1b8497-dcd8-419d-b0e6-11a6037e04a0")
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

    @objid ("e89eace6-77b1-4921-9dd4-c4aa327b94a9")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("fe05909c-a2dc-486b-b2e7-9bbf77ceba48")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("6a3a8efd-a345-47de-86df-815e82e558d2")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
