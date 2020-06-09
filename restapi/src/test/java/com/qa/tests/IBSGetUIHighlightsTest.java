package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.client.AuthIBSnCVIlogin;
import com.qa.client.PostnGetGeneral;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class IBSGetUIHighlightsTest extends TestBase{

	TestBase testbase;
	//	RestClient restClient;

	AuthIBSnCVIlogin auth = new AuthIBSnCVIlogin();
	PostnGetGeneral get = new PostnGetGeneral();

	String ContentTyp; 
	String Accept; 
	String XTenant;
	//	String payload; 
	String serviceURL;
	String baseURLAdmin;

	//	private final Logger logger = LoggerFactory.getLogger(apiUrl);

	@BeforeSuite
	public void setUP() throws ClientProtocolException, IOException {
		setupLog();
		loggerTest = extent.createTest("UIHighlights");
		loggerTest.info("UI Display - Highlights");
		auth.IBSLogin();

		testbase = new TestBase();

		baseURLAdmin = prop.getProperty("URLAdmin");
		System.out.println(baseURLAdmin);
		//		apiUrl = prop.getProperty("serviceURL");
		//		url = serviceUrl+apiUrl;
	}

	@Test(dataProvider = "GETData")
	public void responseBodyTest(String serviceURL, String ContentTyp, String Accept, String XTenant, String payload) throws ParseException{	

		loggerTest = extent.createTest("responseBodyTest");
		loggerTest.assignCategory("UI Display - Highlights");
		
		String accessToken = auth.accessToken;
		get.getMethodforIBS(serviceURL, ContentTyp, Accept, XTenant, baseURLAdmin, accessToken);

		Response resp = TestBase.response;
		loggerTest.info("********************Resopnse Body*******************");
		loggerTest.info("Response Body==>"+resp.getBody());
		loggerTest.info("Response JSON==>"+resp.getBody().asString());
		get.responseBodyAssertion(resp);
	}

	@Test(dataProvider = "GETData")
	public void statusCodeTest(String serviceURL, String ContentTyp, String Accept, String XTenant, String payload) throws ParseException{	
		
		loggerTest = extent.createTest("statusCodeTest");
		loggerTest.assignCategory("UI Display - Highlights");
		
		String accessToken = auth.accessToken;
		get.getMethodforIBS(serviceURL, ContentTyp, Accept, XTenant, baseURLAdmin, accessToken);
		
		Response resp = TestBase.response;
		loggerTest.info("********************Status Code*******************");
		loggerTest.info("Status Code==>"+resp.getStatusCode());
		get.statusCodeAssertion(resp);
	}
	@Test(dataProvider = "GETData")
	public void responseTimeTest(String serviceURL, String ContentTyp, String Accept, String XTenant, String payload) throws ParseException{	
		
		loggerTest = extent.createTest("responseTimeTest");
		loggerTest.assignCategory("UI Display - Highlights");
		
		String accessToken = auth.accessToken;
		get.getMethodforIBS(serviceURL, ContentTyp, Accept, XTenant, baseURLAdmin, accessToken);
		
		Response resp = TestBase.response;
		loggerTest.info("********************Response Time*******************");
		loggerTest.info("Responce Time==>"+resp.getTime());
		get.responseTimeAssertion(resp);
	}

	@Test(dataProvider = "GETData")
	public void contentTypeTest(String serviceURL, String ContentTyp, String Accept, String XTenant, String payload) throws ParseException{	
		
		loggerTest = extent.createTest("contentTypeTest");
		loggerTest.assignCategory("UI Display - Highlights");
		
		String accessToken = auth.accessToken;
		get.getMethodforIBS(serviceURL, ContentTyp, Accept, XTenant, baseURLAdmin, accessToken);
		
		Response resp = TestBase.response;
		loggerTest.info("********************ContentType*******************");
		loggerTest.info("Content Type==>"+resp.contentType());
		get.contentTypeAssertion(resp);

	}

	//		Response response1 = given().auth().preemptive().oauth2(accessToken)
	//				.contentType(ContentTyp)
	//				.header("Accept", Accept)
	//				.header("X-Application-Authentication", "Bearer "+accessToken)
	//				.header("X-Tenant", XTenant)
	//				.when()
	//				.get("https://admin-skill-edge.smartvoicehub.de/svhb/meeting/v1/ui/meetings/");
	////				.get(baseURLadmin+serviceURL);
	//
	//		String responseBody = response1.getBody().asString();
	//		Iterable<io.restassured.http.Header> headerArray = response1.getHeaders();
	//		HashMap<String, String> allHeaders = new HashMap<String, String>();
	//		for(io.restassured.http.Header header: headerArray) {
	//			allHeaders.put(header.getName(), header.getValue());
	//		}
	//		System.out.println("All Headers-->"+ allHeaders);
	//		//	if (response1.getStatusCode() >= 200 && response1.getStatusCode() <= 299) {
	//		//		logger.info("Create Daily Feed Response = " + responseBody);
	//		//	} else {
	//		//		logger.error("Error creating daily feed = {}", responseBody);
	//		//	}
	//		System.out.println("Response is"+response1);
	//		String responsestring1=response1.asString();
	//		System.out.println(response1.getStatusCode());
	//		System.out.println(responsestring1);
	//		System.out.println(responseBody);

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

	
	@DataProvider(name="GETData")
	public String[][] getAPIData() throws IOException{
		String data[][] = get.getAPIPostData("UIHighlights");
		return data;	
	}
}
