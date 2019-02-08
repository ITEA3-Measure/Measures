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

@objid ("22801075-d303-4f52-a31e-bd84041c8b13")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("6d52626a-d255-4868-b5ec-701414a74377")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("eee61428-39d3-49d4-b54a-f830d9c7b6f8")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("f3357e02-b6cc-45bb-a6ca-f840dd6b67d8")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("1507077b-0b13-4cc7-8521-aa0a0770f0b3")
    private Hawk.Client client;

    @objid ("2f08b2a8-b6ff-4219-9901-1e44d4a34e78")
    private ThriftProtocol clientProtocol;

    @objid ("fdf200ba-d1ea-4628-8e4b-a39dcd76672f")
    private String currentInstance;

    @objid ("dee8c681-94c4-4ebc-81ac-e3edb9d12bff")
    private String defaultNamespaces;

    @objid ("c302f169-8852-4c1f-9a39-90cbd558e6e8")
    private String serverUrl;

    @objid ("005b429e-a162-44cd-9eac-682c155b3e4e")
    private String instanceName;

    @objid ("5fcdbc25-cec6-43cb-8220-4999fb8cb5ec")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("4f66d016-2b4d-4810-b2fe-0913fff1364b")
    private String repository;

    @objid ("24a702db-0797-4c74-a523-fb862aa6e982")
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

    @objid ("a86fad60-7f78-406f-98a9-0a12a50fb88f")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query = "var nservices =  Note.all.select(n|'@ComponentEvents'.isSubstringOf(n.Content));"
                + "var result = 0;" + "for(nservice in nservices){"
                + "result = result + nservice.hawkParent.OwnedOperation.size();" + "}" + "return result;";
    }

    @objid ("4aa25783-816c-4da5-a53f-4a232132684e")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("ace9a91f-0739-42ff-b0b1-44f5bccfe2d8")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("54ed1f67-079a-45a3-9e7e-f0b5fdf5a3f5")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("e78cde33-dc7f-49e8-bf2d-932e1616576b")
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

    @objid ("41516900-f510-46b9-aa37-a634028dca11")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("9054892e-c40a-4413-afb2-b4f166458d04")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("37fe8cde-3c4c-4ce9-8a53-9831a8525576")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
