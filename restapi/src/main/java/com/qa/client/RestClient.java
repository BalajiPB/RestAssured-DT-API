package com.qa.client;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {

	//1. Get Method
	public void getFirstMethod(String url) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url); // http GET request
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
		
		//Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code-->"+ statusCode);
		//JSON String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject jsonObject = new JSONObject(responseString);
		System.out.println("Response JSON-->"+ jsonObject);
		//Header
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header: headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("All Headers-->"+ allHeaders);
		
		
	}
}
