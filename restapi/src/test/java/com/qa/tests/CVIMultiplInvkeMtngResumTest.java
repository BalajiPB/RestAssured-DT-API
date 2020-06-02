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
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import com.qa.authLogin.AuthIBSnCVIlogin;
import com.qa.client.AuthIBSnCVIlogin;
import com.qa.client.CVIPostGeneral;
import com.qa.base.TestBase;
import com.qa.xlsData.XLSUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CVIMultiplInvkeMtngResumTest extends TestBase{
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
	CVIPostGeneral post = new CVIPostGeneral();
	String ContentTyp; 
	String Accept; 
	String ApiKey;
	String payload; 
	String serviceURL;
	@BeforeSuite
	public void login() throws IOException {	
		setupLog();
		loggerTest = extent.createTest("Meeting Resume Test Suite");
		auth.CVILogin();

		testbase = new TestBase();

		baseURL = prop.getProperty("URL");
		//		apiUrl = prop.getProperty("serviceURL");
		//		url = serviceUrl+apiUrl;

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

	@Test(dataProvider = "POSTData")
	//	public void postTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{
	public void responseBodyTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{	
		
//		loggerTest = extent.createTest("responseBodyTest");
		
		String token = auth.token;
//		CVIPostGeneral post = new CVIPostGeneral();
		post.postMethodforCVIP(token, ContentTyp, Accept, ApiKey, payload, baseURL, serviceURL);
		Response resp = TestBase.response;
		//		String responseBody = response.getBody().asString();
		//		logger.info("Response Body==>"+responseBody);
		loggerTest.info("********************Resopnse Body*******************");
		loggerTest.info("Response Body==>"+resp.getBody());
		post.responseBodyAssertion(resp);
	}
	@Test(dataProvider = "POSTData")
	//	public void postTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{
	public void statusCodeTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{	
		
//		loggerTest = extent.createTest("statusCodeTest");
		
		String token = auth.token;
//		CVIPostGeneral post = new CVIPostGeneral();
		post.postMethodforCVIP(token, ContentTyp, Accept, ApiKey, payload, baseURL, serviceURL);
		Response resp = TestBase.response;
		//		String responseBody = response.getBody().asString();
		//		logger.info("Response Body==>"+responseBody);
		loggerTest.info("********************Status Code*******************");
		loggerTest.info("Status Code==>"+resp.getStatusCode());
		post.statusCodeAssertion(resp);
	}
	@Test(dataProvider = "POSTData")
	//	public void postTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{
	public void responseTimeTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{	
		
//		loggerTest = extent.createTest("responseTimeTest");
		
		String token = auth.token;
//		CVIPostGeneral post = new CVIPostGeneral();
		post.postMethodforCVIP(token, ContentTyp, Accept, ApiKey, payload, baseURL, serviceURL);
		Response resp = TestBase.response;
		//		String responseBody = response.getBody().asString();
		//		logger.info("Response Body==>"+responseBody);
		loggerTest.info("********************Response Time*******************");
		loggerTest.info("Responce Time==>"+resp.getTime());
		post.responseTimeAssertion(resp);
	}

	@Test(dataProvider = "POSTData")
	//	public void postTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{
	public void contentTypeTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{	
		
//		loggerTest = extent.createTest("contentTypeTest");
		
		String token = auth.token;
//		CVIPostGeneral post = new CVIPostGeneral();
		post.postMethodforCVIP(token, ContentTyp, Accept, ApiKey, payload, baseURL, serviceURL);
		Response resp = TestBase.response;
		//		String responseBody = response.getBody().asString();
		//		logger.info("Response Body==>"+responseBody);
		loggerTest.info("********************ContentType*******************");
		loggerTest.info("Content Type==>"+resp.contentType());
		post.contentTypeAssertion(resp);

	}
	
	@AfterMethod
	public void teardown(ITestResult result) {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			loggerTest.fail(result.getThrowable().getMessage());
		}else {
			loggerTest.pass("Test Passed");
		}
		
		extent.flush();
	}

	@DataProvider(name="POSTData")
	String [][] getAPIData() throws IOException{
		String data[][] = post.getAPIPostData("MeetingResume");
		return data;	}

}

