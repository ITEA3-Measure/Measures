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

@objid ("1adb6f90-80d3-4be0-ae2c-0316ec94a710")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("361096e6-cec0-4efd-bd29-c104277d53b0")
    public static final String SCOPE_SERVERURL = "serverUrl";

    @objid ("11eff90e-d954-4fbe-9f6a-ecc4fabb6347")
    public static final String SCOPE_INSTANCENAME = "instanceName";

    @objid ("4e611668-7339-4681-8fdd-8db6c8b042f3")
    public static final String SCOPE_REPOSITORY = "repository";

    @objid ("0ca62fa6-7d3c-4eb3-8690-537fba929689")
    private Hawk.Client client;

    @objid ("e19073ac-845e-46fa-a3e7-417387bc9671")
    private ThriftProtocol clientProtocol;

    @objid ("f2ef2a31-984e-4971-a57f-60c1af7edf26")
    private String currentInstance;

    @objid ("f47d59dc-d7a8-4661-bde1-05a4a77ff9de")
    private String defaultNamespaces;

    @objid ("3bedf88c-8064-4a9d-b2a7-4e1c8c592c13")
    private String serverUrl;

    @objid ("3a03b51b-f428-4aee-99d9-27911e753bd5")
    private String instanceName;

    @objid ("236fea6f-2c02-45de-9a38-9d15d312a0c6")
    private String query;

    /**
     * (optional) The repository for the query (or * for all repositories).
     */
    @objid ("8c26b461-9cd6-4135-8e18-43b6d828cad7")
    private String repository;

    @objid ("bbb1a12d-c39e-47af-bccc-aaa1c13f5aa8")
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

    @objid ("97381112-f901-4c1e-ae82-cc3ad0c66c33")
    protected void initProperties() {
        this.serverUrl = getProperty(SCOPE_SERVERURL);
        this.instanceName = getProperty(SCOPE_INSTANCENAME);
        this.repository = getProperty(SCOPE_REPOSITORY);
            
        this.query ="return Note.all.select(n|'@DataLayer'.isSubstringOf(n.Content)).size();";
    }

    @objid ("2734d523-b129-4dd7-abc3-c12ea5ea89d5")
    protected void connect(String url, String username, String password) throws Exception {
        clientProtocol = ThriftProtocol.guessFromURL(url);
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        }
        client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
    }

    @objid ("ffed1f26-cc29-421b-ae96-4d6e894ed8a4")
    protected void disconnect() throws Exception {
        if (client != null) {
            final TTransport transport = client.getInputProtocol().getTransport();
            transport.close();
        
            client = null;
            currentInstance = null;
        }
    }

    @objid ("2c522a96-a59b-495e-b390-a2eddb5527da")
    protected void selectInstance(String name) throws Exception {
        checkConnected();
        findInstance(name);
        currentInstance = name;
    }

    @objid ("35ce61dd-0aad-4c88-bf7d-9e42ae01d865")
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

    @objid ("9a287a21-854a-4ac3-a98d-ea88c8444c73")
    private void checkConnected() throws ConnectException {
        if (client == null) {
            throw new ConnectException("Please connect to a Thrift endpoint first!");
        }
    }

    @objid ("9d35ede2-6f4c-4aec-adc0-d245c123cc47")
    protected void checkInstanceSelected() throws ConnectException {
        checkConnected();
        if (currentInstance == null) {
            throw new IllegalArgumentException("No Hawk instance has been selected");
        }
    }

    @objid ("f69c99a5-f7b7-4649-a7cf-94edca9cfc15")
    protected HawkInstance findInstance(final String name) throws Exception {
        for (HawkInstance i : client.listInstances()) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
    }

}
