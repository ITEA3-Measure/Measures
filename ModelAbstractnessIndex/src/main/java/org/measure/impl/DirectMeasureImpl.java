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

@objid ("12b6763c-cc40-48d1-b037-ce5426b4155f")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("79d75a48-5c03-44ea-826e-21118ef45424")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("c7548133-be95-47e4-b5a5-8946ebd4479e")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("f8e96ecd-efe4-499d-9a5b-54185aa67aa3")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("73f8209d-3e9e-4026-9f27-a93cf218dac7")
    private Hawk.Client client;

    @objid ("8f4dc940-1453-4680-a3b9-1ca238a802eb")
    private ThriftProtocol clientProtocol;

    @objid ("a4e0d7d5-4f14-4494-9c31-e69afeefd14b")
    private String currentInstance;

    @objid ("5ca982b4-fe1c-4946-ae30-a108ca89c5ea")
    private String defaultNamespaces;

    @objid ("b77a680d-deab-4836-b3da-359b0d65fe62")
    private String serverUrl;

    @objid ("3d4dcf07-229b-4f99-a7e7-c46d5745ae1b")
    private String instanceName;

    @objid ("fd179b57-0724-448f-95fa-13644d5799da")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("f1b1692d-4489-492e-8ba9-bafb813cd159")
    private String repository;

    @objid ("1de8a267-89c0-48ba-8525-696aae37f333")
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
        
                DoubleMeasurement res = new DoubleMeasurement();
        
                if (qResult.isSetVDouble()) {
                    res.setValue(qResult.getVDouble());
                } else if (qResult.isSetVInteger()) {
                    res.setValue(new Double(qResult.getVInteger()));
                } else if (qResult.isSetVLong()) {
                    res.setValue(new Double(qResult.getVLong()));
                } else if (qResult.isSetVShort()) {
                    res.setValue(new Double(qResult.getVShort()));
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

    @objid ("703c5bd5-b396-4be4-a8fe-9f60c0f1d8bc")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        
        this.defaultNamespaces = "";
        this.query = "var cls =  Class.all;" + "var itrf = Interface.all;" + "if(cls.size + itrf.size > 0){" + "return (cls.select(c|c.IsAbstract).size + itrf.select(c|c.IsAbstract).size)  /( cls.size + itrf.size) * 100;" + "}" + "return 0;";
    }

    @objid ("c29c3f22-4999-4438-8b63-fb873796ba51")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("eade18bd-213a-47b3-b543-d011223818cf")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("b80e7305-9e3e-41c1-a11b-3cba8c46658d")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("bf66516c-e27d-4aa3-9dfd-914f41c11ba2")
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

    @objid ("891ba7c9-5157-4817-82f5-2df994033f64")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("b7e4c7e9-ed5f-4f05-947c-611c045b6899")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("73af2d03-62d3-494c-9ffa-ed291735f7d4")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
