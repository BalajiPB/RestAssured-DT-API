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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import com.qa.authLogin.AuthIBSnCVIlogin;
import com.qa.client.AuthIBSnCVIlogin;
import com.qa.client.PostnGetGeneral;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.base.TestBase;
import com.qa.xlsData.XLSUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CVIInvkeViaTxt extends TestBase{
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
	PostnGetGeneral post = new PostnGetGeneral();
	
	String ContentTyp; 
	String Accept; 
	String ApiKey;
	String payload; 
	String serviceURL;
	@BeforeSuite
	public void login() throws IOException {	
		setupLog();
		loggerTest = extent.createTest("Invoke Meeting via Text");
		loggerTest.info("Meeting-Invoke by text");
		auth.CVILogin();

		testbase = new TestBase();

		baseURL = prop.getProperty("URL");
	}

	@Test(dataProvider = "POSTData")
	public void responseBodyTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{	
		
		loggerTest = extent.createTest("responseBodyTest");
		loggerTest.assignCategory("Meeting Invoke by Text");
		
		String token = auth.token;

		post.postMethodforCVIP(token, ContentTyp, Accept, ApiKey, payload, baseURL, serviceURL);
		Response resp = TestBase.response;
		loggerTest.info("********************Resopnse Body*******************");
		loggerTest.info("Response Body==>"+resp.getBody());
		post.responseBodyAssertion(resp);
	}
	@Test(dataProvider = "POSTData")
	public void statusCodeTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{	
		
		loggerTest = extent.createTest("statusCodeTest");
		loggerTest.assignCategory("Meeting Invoke by Text");

		String token = auth.token;

		post.postMethodforCVIP(token, ContentTyp, Accept, ApiKey, payload, baseURL, serviceURL);
		Response resp = TestBase.response;
		loggerTest.info("********************Status Code*******************");
		loggerTest.info("Status Code==>"+resp.getStatusCode());
		post.statusCodeAssertion(resp);
	}
	@Test(dataProvider = "POSTData")
	public void responseTimeTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{	
		
		loggerTest = extent.createTest("responseTimeTest");
		loggerTest.assignCategory("Meeting Invoke by Text");

		String token = auth.token;

		post.postMethodforCVIP(token, ContentTyp, Accept, ApiKey, payload, baseURL, serviceURL);
		Response resp = TestBase.response;
		loggerTest.info("********************Response Time*******************");
		loggerTest.info("Responce Time==>"+resp.getTime());
		post.responseTimeAssertion(resp);
	}

	@Test(dataProvider = "POSTData")
	public void contentTypeTest(String serviceURL, String ContentTyp, String Accept, String ApiKey, String payload) throws ParseException{	
		
		loggerTest = extent.createTest("contentTypeTest");
		loggerTest.assignCategory("Meeting Invoke by Text");

		String token = auth.token;

		post.postMethodforCVIP(token, ContentTyp, Accept, ApiKey, payload, baseURL, serviceURL);
		Response resp = TestBase.response;
		loggerTest.info("********************ContentType*******************");
		loggerTest.info("Content Type==>"+resp.contentType());
		post.contentTypeAssertion(resp);

	}

	@AfterTest
	public void teardown() {
		extent.flush();
	}
	
	@AfterMethod
	public void checkResults(ITestResult result) {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			loggerTest.log(Status.FAIL, MarkupHelper.createLabel("Test Fail Details", ExtentColor.BROWN));
			loggerTest.log(Status.FAIL, result.getThrowable());
//			loggerTest.fail(result.getThrowable().getMessage());
		}else if(result.getStatus()==ITestResult.SKIP) {
			loggerTest.log(Status.SKIP, MarkupHelper.createLabel("Test Skip Details", ExtentColor.ORANGE));
//			loggerTest.skip(result.getThrowable().getMessage());
			loggerTest.log(Status.SKIP, result.getThrowable());
		}else {
			loggerTest.log(Status.PASS, MarkupHelper.createLabel("Test Pass Details", ExtentColor.GREEN));
//			loggerTest.pass("Test Passed");
			loggerTest.log(Status.PASS, "Test Passed");
		}
		
//		extent.flush();
	}

	@DataProvider(name="POSTData")
	public String[][] getAPIData() throws IOException{
		String data[][] = post.getAPIPostData("InvokeviaTxt");
		return data;	
	}

}

