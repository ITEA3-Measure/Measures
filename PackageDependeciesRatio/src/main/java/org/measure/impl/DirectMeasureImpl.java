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
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

@objid ("0631b366-8546-4cf7-bde5-1dc2108c10c9")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("ede8d2e5-ebdd-44de-a069-5fbde6bdede7")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("4522e520-5b36-40d9-896f-58df7648c011")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("a2fe0167-eea2-43b3-806b-f3ba22773310")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("ed2fff54-499f-4680-be52-a52b6c4ff658")
    private Hawk.Client client;

    @objid ("fcf840b4-ca44-4fa2-9116-18fadab4ffee")
    private ThriftProtocol clientProtocol;

    @objid ("680490b5-a2a6-4df9-81fb-e0131bb11386")
    private String currentInstance;

    @objid ("753b7452-1dfd-4187-98a9-9c5b9f45b70d")
    private String defaultNamespaces;

    @objid ("986b1499-a4dc-4620-b923-de8966db9686")
    private String serverUrl;

    @objid ("a1a49934-c61c-43cf-a479-4269f1c0e668")
    private String instanceName;

    @objid ("94ff7b9f-9a7f-4ef3-96f2-abf6f99895da")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("68fa4cf9-6c42-4a4d-8575-cf6740bae9a1")
    private String repository;

    @objid ("88789071-7b11-4085-a631-67e601175d65")
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
            
            DoubleMeasurement res = new DoubleMeasurement();
            
            if(qResult.isSetVDouble()){
                   res.setValue(qResult.getVDouble());       
            }else if(qResult.isSetVInteger()){
                res.setValue(new Double(qResult.getVInteger()));      
            }else if(qResult.isSetVLong()){
                res.setValue(new Double(qResult.getVLong()));      
            }else if(qResult.isSetVShort()){
                res.setValue(new Double(qResult.getVShort()));      
            }
               
            result.add(res);
            
            
            // disconnect
            disconnect();
        
        } catch (Throwable e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
        return result;
    }

    @objid ("62626ef7-4381-4416-90ac-e08f3da2e344")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="var cls  = Package.all;"
        + "if(cls.notEmpty()){"
            + "var nbDep = 0;"
            + "for(dp in Dependency.all){"
                + "if(dp.hawkParent.isTypeOf(Class)){"            
                    + "nbDep = nbDep+1;"
                + "}"
            + "}"
            + "return nbDep / cls.size;"
        + "}"               
           + "return 0;";
    }

    @objid ("b50ea888-98c1-443a-9f56-6f3b7135b2ce")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("5873c4fd-c385-42e5-9999-3098cc6181a5")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("b8c67ce5-f198-4545-9dd5-1c451f244599")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("c0ba19e3-9993-4ac3-b3c2-4db548830c24")
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

    @objid ("6d1347a9-be63-4cd3-95da-cc5dfc5fb9bb")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("e6ab0dbe-bf42-4bf5-badf-18a58d1e3e75")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("f798315d-80fa-44f1-a98e-a9c8ad5cc235")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
