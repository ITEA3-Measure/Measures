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

@objid ("795d8e30-1ac6-43b1-be55-9ac18369d6a6")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("63e0528c-decd-4c30-826d-9d6b77e6239f")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("fcd656cd-6e35-4f8b-ac68-81b1666030ab")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("fbeb4d02-7321-430b-814a-81666d2fa89e")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("1455effb-c1cd-4196-b254-922a30dc2cdf")
    private Hawk.Client client;

    @objid ("d1c390fb-684b-4393-a553-7998265add64")
    private ThriftProtocol clientProtocol;

    @objid ("e17be4fe-53db-4864-8fd8-67dca787239d")
    private String currentInstance;

    @objid ("1885e764-744a-4343-a28d-cec29ee96b87")
    private String defaultNamespaces;

    @objid ("4f28e0ef-5d53-46ae-9d71-fa75527790b0")
    private String serverUrl;

    @objid ("a37ba04f-55ea-496e-a51f-48b78b464d42")
    private String instanceName;

    @objid ("73ba10c4-898c-44cd-9ea7-57e0b4752b0e")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("41233f84-4b18-41bb-af39-2d34f9494986")
    private String repository;

    @objid ("be998849-b6d8-472d-bead-6321d060ac81")
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

    @objid ("edc70a0e-f3a3-44f2-a6d7-d0898ac06850")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
        this.query ="return Note.all.select(n|'@ComponentItem'.isSubstringOf(n.Content)).size();";
    }

    @objid ("3d9211ff-9ccc-4b40-92b8-1553c5e09864")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("4b1a2bba-028b-4e6c-a382-553ef7c8cbf2")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("513e68a9-3b8c-4a7d-9f46-f7996fbaaaca")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("b34dff49-5c53-4963-828d-2ccc383604ea")
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

    @objid ("28fc8c23-3661-4ca0-b7c6-c9a9649f7a47")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("0f17ed9d-811e-42b0-ab3b-2b873ea5ded5")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("1b857f13-8607-435b-a6be-5f2769162e1a")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
