package org.measure.power;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
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
		client.close();
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
		HttpResponse response = client.execute(host, request);
        HttpEntity entity = response.getEntity();
        String message = EntityUtils.toString(entity);
        int status = response.getStatusLine().getStatusCode();
        EntityUtils.consume(entity);
        if (status == 200) {
        	return Arrays.asList(mapper.fromJson(message, EmitPowerMessage[].class));
        } else {
        	throw new Exception("error " + status + ": " + message);
        }        
	}
	
	private int getStatus(HttpRequestBase request) throws Exception {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		return status;
	}

}