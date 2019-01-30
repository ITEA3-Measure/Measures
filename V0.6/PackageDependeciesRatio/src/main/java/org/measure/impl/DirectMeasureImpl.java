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

@objid ("62a477a7-b5ce-472c-8254-b46189bd15da")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("378c3ed6-e5da-4a42-ac23-c3048703be9a")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("b22856a1-555a-44b0-8089-31de3942e1d7")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("18fafca1-c46d-423e-98a9-3f42bd2dcf64")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("47301d92-44b9-46d0-b744-9d568b3db81e")
    private Hawk.Client client;

    @objid ("a46e4879-e7a1-4a2d-802f-9bad82969e6b")
    private ThriftProtocol clientProtocol;

    @objid ("cbfdcb4d-58a0-4156-80e6-ed9fef01953a")
    private String currentInstance;

    @objid ("59916dce-238b-4817-96fa-f1ef411b3239")
    private String defaultNamespaces;

    @objid ("e4a84622-b507-42ff-bbdc-ba1b2d3ff9e4")
    private String serverUrl;

    @objid ("43013286-5ab1-4066-a60a-9aa0d0608f7d")
    private String instanceName;

    @objid ("00a3496a-d5cd-47ca-a9aa-1003597bf48a")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("96d71898-3ba1-4fcf-a2b1-944ddc6d25e0")
    private String repository;

    @objid ("c53cd592-a6a8-4697-bdf5-826a90498576")
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

    @objid ("a0c7702e-0d0f-48ef-a9c1-543b00982e84")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        
        this.query = "var cls  = Package.all;" + "if(cls.notEmpty()){" + "var nbDep = 0;" + "for(dp in Dependency.all){" + "if(dp.hawkParent.isTypeOf(Class)){" + "nbDep = nbDep+1;" + "}" + "}" + "return nbDep / cls.size;" + "}" + "return 0;";
    }

    @objid ("6f10eb72-23c9-4885-8104-f0712dc61375")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("912050cb-6441-47cd-825a-fa96f76e4901")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("ecd6debb-3b4e-4722-9105-6206be0dd53c")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("a7e2eac0-aad5-4396-b350-7da713c6479c")
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

    @objid ("3417bc7e-34e6-4908-80d8-5d8ee33cf8f8")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("6f5469d1-3860-4695-a0e9-3b8908af03f9")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("e8df06c3-b9d3-45eb-8165-6d6b53064bd9")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
