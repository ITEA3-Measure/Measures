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

@objid ("088e9b09-9f68-4619-8895-e46e5c9dba94")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("64512a6e-dba3-4407-bbfa-c6887c23dc82")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("329e7339-9767-4844-9b53-1df776292a68")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("e239e4ea-c2d7-4471-a3d1-b4ea1f3a81d3")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("c4306f9c-6151-4658-84da-f11aa3a29344")
    private Hawk.Client client;

    @objid ("94760604-ac24-402b-a654-aba651032346")
    private ThriftProtocol clientProtocol;

    @objid ("51334606-af7b-41e7-a17c-08b29d28cab6")
    private String currentInstance;

    @objid ("24fcf60e-6fc8-4ff4-974c-82121297a85f")
    private String defaultNamespaces;

    @objid ("7aa1eff6-eff7-4af4-a5a2-e6f03e85947b")
    private String serverUrl;

    @objid ("d6ca6152-76a1-4238-b752-f2ecf5dc186b")
    private String instanceName;

    @objid ("c08af77f-253e-4ead-8a13-6acd69e4d40f")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("403d08e2-0085-4d67-b869-8d14f2307e96")
    private String repository;

    @objid ("e686536d-7353-4558-ae08-491d00eb524e")
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
        
            try  {
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
            } catch(Exception e){
                e.printStackTrace();
            }
        
             
            
            // disconnect
            disconnect();
        
        } catch (Throwable e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
        return result;
    }

    @objid ("fabb4e0e-6df7-4b56-815f-e31faac070be")
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

    @objid ("7974809e-e35d-4b8d-a4e8-9c45cd4a1649")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("9d7ede20-4dba-40bd-accb-123fb189bf27")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("617a9cb1-9adf-40fb-a21d-0b55af56390c")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("a28e1214-7f75-49d1-8ad5-4f3a8939c8a3")
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

    @objid ("0d75fb41-e08d-4438-ad9a-865282b711a6")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("a6feb2be-1a89-4898-9a0d-9ca07a763a17")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("d64bdfb6-4f49-4394-a536-79256a8c5019")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
