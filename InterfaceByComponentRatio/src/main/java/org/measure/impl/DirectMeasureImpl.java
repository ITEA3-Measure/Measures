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

@objid ("1c5873c9-7926-4f2d-8b66-ea58d32a5a30")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("3db2cc18-4d71-451d-8c26-64c0674a6fc6")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("c8f8adee-55d9-4b25-8f78-b28087c74463")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("5d5e1dd1-5dbc-49b0-bf96-f19ac8c6375b")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("2a23d2ee-7c2e-484c-b7aa-5c5f83c3d20c")
    private Hawk.Client client;

    @objid ("94d8a30b-1948-445c-bb18-641794a0415a")
    private ThriftProtocol clientProtocol;

    @objid ("67850cd0-95c0-4641-929c-45e5219428e5")
    private String currentInstance;

    @objid ("3620fd9f-f83a-4d27-85eb-5b7fc7c554f8")
    private String defaultNamespaces;

    @objid ("3b4a9627-7465-421e-acb0-faf3ee920667")
    private String serverUrl;

    @objid ("c5c17ebe-28ba-4b9a-b49a-20fd2bdad4e2")
    private String instanceName;

    @objid ("ad0f147f-18bc-4612-a15d-75b2df2c5585")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("59ab8958-a8da-4794-b01d-e3d543f965c4")
    private String repository;

    @objid ("1d302c15-e89d-46b8-b59c-91f022eff6de")
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

    @objid ("1fcb4095-2276-4659-8c9c-a6c9bd232304")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        		this.query = "var namespaces = NameSpace.all.select(c|c.Realized.size > 0);"
        				+ "var nbdep = 0;"
        				+ "if(namespaces.notEmpty()){"
        				+ "for(nsp in namespaces){"
        				+ "if(nsp.Realized.notEmpty()){"
        				+ " nbdep = nbdep + nsp.Realized.size;"
        				+ "}"
        				+ "}"
        				+ "return nbdep / namespaces.size();"
        				+ "}"
        				+ "return 0.0;";
    }

    @objid ("a4fe2110-804c-484e-bc40-cafd64e2dbcc")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("bb2f8b8d-ba7e-4fe1-8793-3afeb5b9611c")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("b54c08f2-c3a0-4611-a35e-3b1f0348b12e")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("feea26df-3f2b-479c-b449-0fc851b4d7d9")
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

    @objid ("8f979998-14af-4696-ab81-c8078294fa7b")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("19194138-a67c-4285-925f-b3b9f34c25f0")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("eda0cc99-32c3-4037-a9ec-783d64dbf82c")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
