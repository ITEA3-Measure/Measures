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

@objid ("8e2b26e8-12b7-4a0e-94f9-50ccf7551ae9")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("483e7ff3-3ac3-46c5-983f-39d1ac3f82f4")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("c3be5c61-542e-4571-a519-db3b2765727c")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("c1dfeeca-0495-4530-ace6-16ea855dbfda")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("0eaee533-d498-433c-ae27-5f1308461bc6")
    private Hawk.Client client;

    @objid ("26ad16b0-0b65-42bf-9c7f-025f4ec29052")
    private ThriftProtocol clientProtocol;

    @objid ("a6f27db2-6381-47ca-aa96-09060f47a6fa")
    private String currentInstance;

    @objid ("6130447a-01cc-47f0-9746-ac3804b88cdc")
    private String defaultNamespaces;

    @objid ("ddc48e19-d806-4ae2-9177-c326b10737a7")
    private String serverUrl;

    @objid ("90c90f68-6690-4697-8776-be08b7b9d535")
    private String instanceName;

    @objid ("8b356402-94cf-4043-807f-7e546886b0fc")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("84f6547a-9370-437e-831d-b15e1ea3f307")
    private String repository;

    @objid ("8cb47897-f776-4181-b85e-549cb1e64aba")
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
        } catch (Throwable e) {
           e.printStackTrace();
        }
            
            // disconnect
            disconnect();
        return result;
    }

    @objid ("3f03dced-cf48-411a-a452-435dcdbe9d15")
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

    @objid ("3dd1269d-c103-4a69-8bb8-279ee9b86464")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("2991b169-fd81-4c45-810e-04815d28bd6f")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("a47b08c4-9d37-42b3-ab70-43de71ecd675")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("d3bce668-c070-4de6-b7cb-e9dc16c9e4ea")
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

    @objid ("146a61fb-05cc-4a27-a699-17cf161ec7f4")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("be3cc0ed-c3e5-4964-b7ed-d06ebbcfd80f")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("794cf699-0d59-48bf-90e6-5808d1402f9c")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
