package org.measure.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class EmitClient {

	private Gson mapper;
	
    private HttpHost host;
    
    private CloseableHttpClient client;
	
    private UsernamePasswordCredentials credentials;
    
    private String toolname;
    
	public EmitClient(String protocol, String hostname, int port, String toolname, String username, String password) {
		mapper = new Gson();
        host = new HttpHost(hostname, port, protocol);
        client = HttpClients.createDefault();
        credentials = new UsernamePasswordCredentials(username, password);
        this.toolname = toolname;
	}

	public void setUp() throws Exception {
		this.doIndex();
		this.doUserAuth();
	}
	
	public void tearDown() throws Exception {
		this.doUserClear();
		// client.close();
	}

	private void doIndex() throws Exception {
        HttpGet request = new HttpGet("/" + toolname);
        HttpResponse response = client.execute(host, request);
        HttpEntity entity = response.getEntity();
        EntityUtils.consume(entity);
	}
	
	private int doUserAuth() throws Exception {
		 HttpPost request = new HttpPost("/" + toolname + "/j_security_check");
         List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
         parameters.add(new BasicNameValuePair("j_username", credentials.getUserName()));
         parameters.add(new BasicNameValuePair("j_password", credentials.getPassword()));
         request.setEntity(new UrlEncodedFormEntity(parameters));
         return this.getStatus(request);
	}
	
	private int doUserClear() throws Exception {
        HttpGet request = new HttpGet("/" + toolname + "/user/clear");
        return this.getStatus(request);
	}
	
	public List<EmitPowerMessage> getMessages(String topic, Long issued) throws Exception {
		if (topic == null) throw new NullPointerException("undefined string parameter 'topic'");
		if (issued == null) throw new NullPointerException("undefined timestamp parameter 'issued'");
		URIBuilder builder = new URIBuilder("/" + toolname + "/messages/search");
		builder.addParameter("topic", topic);
		builder.addParameter("started", issued.toString());
        HttpGet request = new HttpGet(builder.build());
        return this.getList(EmitPowerMessage[].class, request);       
	}
	
	public List<EmitPowerMessage> getMessages(String topic, Long started, Long stopped) throws Exception {
		URIBuilder builder = new URIBuilder("/" + toolname + "/messages/search");
		builder.addParameter("topic", topic);
		builder.addParameter("started", started.toString());
		builder.addParameter("stopped", stopped.toString());
        HttpGet request = new HttpGet(builder.build());
        return this.getList(EmitPowerMessage[].class, request);       
	}
	
	public Long getTimestamp() throws Exception {
		URIBuilder builder = new URIBuilder("/" + toolname + "/timestamp");
        HttpGet request = new HttpGet(builder.build());
        return this.getLong(request);
	}
	
	private <T> T getObject(Class<T> type, HttpRequestBase request) throws Exception {
		HttpResponse response = client.execute(host, request);
        HttpEntity entity = response.getEntity();
        String message = EntityUtils.toString(entity);
        int status = response.getStatusLine().getStatusCode();
        EntityUtils.consume(entity);
        if (status == 200) {
        	return mapper.fromJson(message, type);
        } else {
        	throw new Exception(status + ": " + message);
        }
	}
	
	private <T> List<T> getList(Class<T[]> type, HttpRequestBase request) throws Exception {
		HttpResponse response = client.execute(host, request);
        HttpEntity entity = response.getEntity();
        String message = EntityUtils.toString(entity);
        int status = response.getStatusLine().getStatusCode();
        EntityUtils.consume(entity);
        if (status == 200) {
        	return Arrays.asList(mapper.fromJson(message, type));
        } else {
        	throw new Exception(status + ": " + message);
        }
	}
	
	private Boolean getBoolean(HttpRequestBase request) throws Exception {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		String message = EntityUtils.toString(entity);
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		if (status == 200) {
			return Boolean.valueOf(message);
		} else {
			throw new Exception(status + ": " + message);
		}
	}
	
	private Integer getInteger(HttpRequestBase request) throws Exception {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		String message = EntityUtils.toString(entity);
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		if (status == 200) {
			return Integer.valueOf(message);
		} else {
			throw new Exception(status + ": " + message);
		}
	}
	
	private Long getLong(HttpRequestBase request) throws Exception {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		String message = EntityUtils.toString(entity);
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		if (status == 200) {
			return Long.valueOf(message);
		} else {
			throw new Exception(status + ": " + message);
		}
	}

	private UUID getUUID(HttpRequestBase request) throws Exception {
		return UUID.fromString(this.getString(request));
	}

	private String getString(HttpRequestBase request) throws Exception {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		String message = EntityUtils.toString(entity);
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		if (status == 200) {
			return this.doClean(message);
		} else {
			throw new Exception(status + ": " + message);
		}
	}
	
	private int getStatus(HttpRequestBase request) throws Exception {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		return status;
	}
	
	private String doClean(String string) {
		String clean = string.trim();
		if (clean.startsWith("\"")) {
			if (clean.startsWith("\"")) {
				return clean.substring(1, clean.length() - 1);
			} else {
				return clean.substring(1, clean.length());
			}
		} else if (clean.startsWith("\"")) {
			return clean.substring(0, clean.length() - 1);
		} else {
			return clean;
		}
	}

}