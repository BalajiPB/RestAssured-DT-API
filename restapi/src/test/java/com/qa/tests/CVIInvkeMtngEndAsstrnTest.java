package com.qa.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.qa.authLogin.AuthIBSnCVIlogin;
import com.qa.client.AuthIBSnCVIlogin;
import com.qa.client.PostnGetGeneral;
import com.qa.base.TestBase;
import com.qa.xlsData.XLSUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CVIInvkeMtngEndAsstrnTest extends TestBase{
	TestBase testbase;
	String baseURL;
	String apiUrl;
	String url;
	String accessToken;
	String tokenType;
	String refreshToken;
	String scope1;
//	private final Logger logger = LoggerFactory.getLogger(apiUrl);
	AuthIBSnCVIlogin auth = new AuthIBSnCVIlogin();

	String ContentTyp;
	String Accept; 
	String ApiKey;
	String payload; 
	String serviceURL;

	@BeforeSuite
	public void login() throws IOException {	
		setupLog();
		
		auth.CVILogin();

		testbase = new TestBase();

		baseURL = prop.getProperty("URL");
		//		apiUrl = prop.getProperty("serviceURL");
		//		url = serviceUrl+apiUrl;

		String token = auth.token;

		PostnGetGeneral post = new PostnGetGeneral();
		String data[][] = post.getAPIPostData("MeetingEnd");
		serviceURL = data[0][0];
		ContentTyp = data[0][1]; 
		Accept = data[0][2];
		ApiKey = data[0][3];
		payload = data[0][4];

		post.postMethodforCVIP(token, ContentTyp, Accept, ApiKey, payload, baseURL, serviceURL);


	}


	//	@BeforeMethod
	//	public void setUP() throws ClientProtocolException, IOException {
	//		auth.CVILogin();
	//
	//		testbase = new TestBase();
	//
	//		baseURL = prop.getProperty("URL");
	//		//		apiUrl = prop.getProperty("serviceURL");
	//		//		url = serviceUrl+apiUrl;
	//
	//		String token = auth.token;
	//	}

	@Test
	public void responseBodyTest() {
		logger.info("*******************Response Body*****************");
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>"+responseBody);
		Assert.assertTrue(responseBody!= null);
	}

	@Test
	public void statusCodeTest() {
		logger.info("*******************Status Code*****************");
		int statusCode = response.getStatusCode();
		logger.info("Status Code==>"+statusCode);
		Assert.assertEquals(statusCode, 200);
	}

	@Test
	public void responseTimeTest() {
		logger.info("*******************Response Time*****************");
		long responseTime = response.getTime();
		logger.info("Response Body==>"+responseTime);
		Assert.assertTrue(responseTime < 2000);
	}

	@Test
	public void contentTypeTest() {
		logger.info("*******************Content Type*****************");
		String contentType = response.getHeader("Content-Type");
		logger.info("Response Body==>"+contentType);
		Assert.assertTrue(contentType != null);
	}

}

