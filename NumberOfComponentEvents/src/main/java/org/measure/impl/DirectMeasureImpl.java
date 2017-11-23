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

@objid ("377f413c-2656-468a-b236-0811ab68ea90")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("d86c0983-5155-4197-9308-a74fefe71077")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("2cf4e395-690a-410c-b2d6-f2e7dd9d2d57")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("b06d74c8-91fe-421a-a477-fbd295a2b0f0")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("03d1084c-80ca-4a99-ad66-20b012c04a41")
    private Hawk.Client client;

    @objid ("6cdaf9a1-9262-4472-89e6-5f74f77d0bd2")
    private ThriftProtocol clientProtocol;

    @objid ("0aef3546-968b-481e-816d-7599c53de90c")
    private String currentInstance;

    @objid ("a910bd88-5c02-45e3-b88e-8962847be3ba")
    private String defaultNamespaces;

    @objid ("c04c9613-3264-460d-833b-642afe218aff")
    private String serverUrl;

    @objid ("753e67eb-4c8b-44b7-834f-3aa3cc1b1c8e")
    private String instanceName;

    @objid ("3fb25c6e-10f0-4549-a0f4-aff141fadac0")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("4bfbf2e7-375e-407c-860f-8af7136f477f")
    private String repository;

    @objid ("26c318b1-0b9a-45c5-9319-165af52a19e7")
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
            res.setValue(qResult.getVInteger());        
            result.add(res);
            
            // disconnect
            disconnect();
        
        } catch (Throwable e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
        return result;
    }

    @objid ("a835d247-27ae-4004-b619-f4c0c66a7eb4")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@ComponentEvents'.isSubstringOf(n.Content)).size();";
    }

    @objid ("ea4dc318-b2da-40c4-92a1-0974fd90a8d5")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("e2f5d1d5-0d07-4fe9-9661-2da160abeca4")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("507763da-adba-45d2-b04f-21a1bb6f59fc")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("03781151-a833-4972-9334-994d29e70ad2")
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

    @objid ("637e335f-149a-4617-a1dd-edb296fc46d8")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("3dc06185-9be4-4cb2-9610-bb2810c49051")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("e8501fc3-7aaa-4286-b8dd-bf23799706f5")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
