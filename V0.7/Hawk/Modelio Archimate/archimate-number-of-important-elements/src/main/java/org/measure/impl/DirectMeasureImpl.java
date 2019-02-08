package org.measure.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.thrift.transport.TTransport;
import org.hawk.service.api.Hawk;
import org.hawk.service.api.HawkInstance;
import org.hawk.service.api.HawkQueryOptions;
import org.hawk.service.api.QueryResult;
import org.hawk.service.api.utils.APIUtils;
import org.hawk.service.api.utils.APIUtils.ThriftProtocol;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

import com.google.common.base.Charsets;


public class DirectMeasureImpl extends DirectMeasure {

	public static final String SCOPE_SERVERURL = "serverUrl";

	public static final String SCOPE_INSTANCENAME = "instanceName";

	public static final String SCOPE_REPOSITORY = "repository";

	public static final String SCOPE_DEFAULT_NAMESPACES = "defaultNamespaces";
	public static final String SCOPE_IMPORTANCE_VALUE = "importanceValue";

	private Hawk.Client client;

	private ThriftProtocol clientProtocol;

	private String currentInstance;

	private String defaultNamespaces;

	private String serverUrl;

	private String instanceName;

	private String importanceValue;

	private String query;

	/**
	 * (optional) The repository for the query (or * for all repositories).
	 */
	private String repository;

	private final Map<String, String> loadedQueries = new HashMap<>();

	public DirectMeasureImpl() {
		// We need to load these while the class loader is still open
		preload("Archimate_ImportanceScore.eol");


	}

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
				if (qResult.isSetVDouble()) {
					DoubleMeasurement res = new DoubleMeasurement();
					res.setValue(qResult.getVDouble());
					result.add(res);
				} else {
					IntegerMeasurement res = new IntegerMeasurement();
					res.setValue(qResult.getVInteger());
					result.add(res);
				}
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

	protected void initProperties() {
		this.serverUrl = getProperty(SCOPE_SERVERURL);
		this.instanceName = getProperty(SCOPE_INSTANCENAME);
		this.repository = getProperty(SCOPE_REPOSITORY);
		this.defaultNamespaces = getProperty(SCOPE_DEFAULT_NAMESPACES);
		this.importanceValue = getProperty(SCOPE_IMPORTANCE_VALUE);
		this.query = "return Concept.latest.all" + 
				".select(c|c.Name.isDefined() and not c.relatedTo.isEmpty)" + 
				".collect(c | c.elementScore() * c.rScore()).select( c | c >= " + this.importanceValue + ").size.asDouble(); " + 

			loadedQueries.get("Archimate_ImportanceScore.eol");
	}

	private void preload(final String queryName) {
		try (
				InputStream is = this.getClass().getResourceAsStream(queryName);
				InputStreamReader isR = new InputStreamReader(is, Charsets.UTF_8);
				BufferedReader bR = new BufferedReader(isR)
				) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = bR.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			loadedQueries.put(queryName, sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void connect(String url, String username, String password) throws Exception {
		clientProtocol = ThriftProtocol.guessFromURL(url);
		if (client != null) {
			final TTransport transport = client.getInputProtocol().getTransport();
			transport.close();
		}
		client = APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, "", "");
	}

	protected void disconnect() throws Exception {
		if (client != null) {
			final TTransport transport = client.getInputProtocol().getTransport();
			transport.close();

			client = null;
			currentInstance = null;
		}
	}

	protected void selectInstance(String name) throws Exception {
		checkConnected();
		findInstance(name);
		currentInstance = name;
	}

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
		return client.query(currentInstance, query, "org.hawk.timeaware.queries.TimeAwareEOLQueryEngine", opts);
	}

	private void checkConnected() throws ConnectException {
		if (client == null) {
			throw new ConnectException("Please connect to a Thrift endpoint first!");
		}
	}

	protected void checkInstanceSelected() throws ConnectException {
		checkConnected();
		if (currentInstance == null) {
			throw new IllegalArgumentException("No Hawk instance has been selected");
		}
	}

	protected HawkInstance findInstance(final String name) throws Exception {
		for (HawkInstance i : client.listInstances()) {
			if (i.name.equals(name)) {
				return i;
			}
		}
		throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
	}

}
