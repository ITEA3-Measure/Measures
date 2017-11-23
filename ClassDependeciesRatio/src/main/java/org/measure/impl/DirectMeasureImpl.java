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

@objid ("47158328-0f10-49dd-aa2d-6e51d5c0929f")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("5f9ccdd3-3489-4565-bcc7-6ae86a9e06d4")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("be85c999-c9ca-47c6-83c8-19d9ed302ced")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("36c697c3-9fe4-4287-b8fb-b43d5656a0a2")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("b0fcdd96-39e9-45a6-a5d1-a232f382b14e")
    private Hawk.Client client;

    @objid ("0437c851-dc66-4275-addf-4a7350491b5c")
    private ThriftProtocol clientProtocol;

    @objid ("0f5b6a52-1561-4eb6-8377-74c99624d61a")
    private String currentInstance;

    @objid ("4e0876be-d133-4d40-80c4-48888efe7b39")
    private String defaultNamespaces;

    @objid ("203edd5f-699c-43a3-8736-6cc302ea14b5")
    private String serverUrl;

    @objid ("0a927377-5ff5-49a1-9635-8930b4cecebd")
    private String instanceName;

    @objid ("7880fec1-a236-4b98-afe1-a7b2330e7016")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("29e8dbcf-fb6f-4a12-9bb4-2ba0c91e934d")
    private String repository;

    @objid ("2e09a39e-7596-482c-9dcf-133ae15c631f")
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

    @objid ("6c98bc64-7bf3-4f03-aff4-e1979473a53a")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        //Number of Requirement        
        //Total number of Requirement defined in the selected scope
        this.defaultNamespaces = "modelio://Modeliosoft.Analyst/2.0.00";
        this.query ="var cls  = Class.all.select(c|c.isTypeOf(Class));"
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

    @objid ("d341d732-98d3-453c-a8ce-36f35b33cc7a")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("99568664-bc7e-4996-80c8-5fa3ae13fbaf")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("44312c30-73b0-4c99-83e5-753f8f1d0e23")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("9a2fc4f6-a23b-4d27-acf2-9f8731e0e6a4")
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

    @objid ("cbeffb83-19ce-4400-948a-d797d9bf45ec")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("23350b5e-62c6-4f7f-b8b5-54887f3d71fb")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("4087fedd-fec8-42e9-9feb-e02e4e2ca735")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
