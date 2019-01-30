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

@objid ("1c8dba5a-2f44-4e1d-a591-ead7c0b1658b")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("0cf01137-4a3c-469e-bdeb-77c3a40f7525")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("c2728cd6-04ec-497f-b29e-f9f2146bc24a")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("1d5da55a-f612-494a-b739-4c6b479ccad3")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("74095f9e-a8ee-41b4-9a78-f86f07ce213d")
    private Hawk.Client client;

    @objid ("da8af073-6a17-492e-80db-a776168a417d")
    private ThriftProtocol clientProtocol;

    @objid ("52f5298b-3225-4505-bbc9-d633155e4836")
    private String currentInstance;

    @objid ("2b8f57b5-76f6-4c11-b938-054240413102")
    private String defaultNamespaces;

    @objid ("46ff8d0d-baf4-4852-ae9c-828b0c998d4a")
    private String serverUrl;

    @objid ("5e64caf6-f5c2-4194-9ce3-59cf41f9a1b3")
    private String instanceName;

    @objid ("8fad3783-d0d2-4053-83a2-91d2b0e8f6a7")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("3fa50876-b5f7-4dd0-9ff9-17e504669c43")
    private String repository;

    @objid ("44b497d8-ab71-4bc4-85c4-9b6181834208")
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
                // Execute query
                QueryResult qResult = executeQuery();
        
                IntegerMeasurement res = new IntegerMeasurement();
                res.setValue(qResult.getVInteger());
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

    @objid ("9e620a18-9e59-4c9e-a272-d75df539ece2")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="var nservices =  Note.all.select(n|'@ComponentParameters'.isSubstringOf(n.Content));"
        + "var result = 0;"
        + "for(nservice in nservices){"
        + "result = result + nservice.hawkParent.OwnedOperation.size();"
        + "}"
        + "return result;";
    }

    @objid ("4e0db2b1-21f4-4a88-92c6-fa9be69955f0")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("32e7dc2e-dbc4-4cc6-8d3d-41c210adf0b0")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("1f219fb6-ccc4-4df7-838d-fdb5e55ac892")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("fe6d181f-5ef8-44cf-8f69-934ac18711e3")
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

    @objid ("9679465c-015b-4d64-85d5-85b8d68a9391")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("0110f776-a513-46bb-b03b-3d1ce2b18c60")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("40efd1b4-db7f-4f5b-99cb-7178159ad791")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
