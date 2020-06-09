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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.client.AuthIBSnCVIlogin;
import com.qa.client.PostnGetGeneral;
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
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class IBSGetMeetingsTest extends TestBase{

	TestBase testbase;
	//	RestClient restClient;

	AuthIBSnCVIlogin auth = new AuthIBSnCVIlogin();
	PostnGetGeneral get = new PostnGetGeneral();

	String ContentTyp; 
	String Accept; 
	String XTenant;
	//	String payload; 
	String serviceURL;
	String baseURL;

	//	private final Logger logger = LoggerFactory.getLogger(apiUrl);

	@BeforeSuite
	public void setUP() throws ClientProtocolException, IOException {
		setupLog();
		loggerTest = extent.createTest("UI Meeting Info");
		auth.IBSLogin();

		testbase = new TestBase();

		baseURL = prop.getProperty("URL");
//		System.out.println(baseURLAdmin);
		//		apiUrl = prop.getProperty("serviceURL");
		//		url = serviceUrl+apiUrl;
	}

//	@Test(dataProvider = "GETData")
//	public void responseBodyTest(String serviceURL, String ContentTyp, String Accept, String XTenant, String payload) throws ParseException{	
//
//		String accessToken = auth.accessToken;
////		get.getMethodforIBS(serviceURL, ContentTyp, Accept, XTenant, baseURL, accessToken);
//		httpRequest = RestAssured.given().auth().preemptive().oauth2(accessToken);
//		//		Response response1 = given().auth().preemptive().oauth2(accessToken)
//		httpRequest.contentType(ContentTyp)
//		.header("Accept", Accept)
//		.header("Authorization", "Bearer "+accessToken);
////		.header("X-Tenant", XTenant);
//
//		response = httpRequest.request(Method.GET, baseURL+serviceURL);		
//
//		Response resp = TestBase.response;
//		loggerTest.info("********************Resopnse Body*******************");
//		loggerTest.info("Response Body==>"+resp.getBody());
//		get.responseBodyAssertion(resp);
//	}

	@Test(dataProvider = "GETData")
	public void statusCodeTest(String serviceURL, String ContentTyp, String Accept, String XTenant, String payload) throws ParseException{	
		//comment to be added
		String accessToken = auth.accessToken;
//		get.getMethodforIBS(serviceURL, ContentTyp, Accept, XTenant, baseURL, accessToken);
//		httpRequest = RestAssured.given().auth().preemptive().oauth2(accessToken);
//		//		Response response1 = given().auth().preemptive().oauth2(accessToken)
//		httpRequest.contentType(ContentTyp)
//		.header("Accept", Accept)
//		.header("Authorization", "Bearer "+accessToken);
////		.header("X-Tenant", XTenant);
//
//		response = httpRequest.request(Method.GET, baseURL+serviceURL);		
//
//		Response resp = TestBase.response;
//		loggerTest.info("********************Status Code*******************");
//		loggerTest.info("Status Code==>"+resp.getStatusCode());
//		get.statusCodeAssertion(resp);
//	}
//	@Test(dataProvider = "GETData")
//	public void responseTimeTest(String serviceURL, String ContentTyp, String Accept, String XTenant, String payload) throws ParseException{	
//		
//		String accessToken = auth.accessToken;
////		get.getMethodforIBS(serviceURL, ContentTyp, Accept, XTenant, baseURL, accessToken);
//		httpRequest = RestAssured.given().auth().preemptive().oauth2(accessToken);
//		//		Response response1 = given().auth().preemptive().oauth2(accessToken)
//		httpRequest.contentType(ContentTyp)
//		.header("Accept", Accept)
//		.header("Authorization", "Bearer "+accessToken);
////		.header("X-Tenant", XTenant);
//
//		response = httpRequest.request(Method.GET, baseURL+serviceURL);		
//
//		Response resp = TestBase.response;
//		loggerTest.info("********************Response Time*******************");
//		loggerTest.info("Responce Time==>"+resp.getTime());
//		get.responseTimeAssertion(resp);
//	}
//
//	@Test(dataProvider = "GETData")
//	public void contentTypeTest(String serviceURL, String ContentTyp, String Accept, String XTenant, String payload) throws ParseException{	
//		
//		String accessToken = auth.accessToken;
////		get.getMethodforIBS(serviceURL, ContentTyp, Accept, XTenant, baseURL, accessToken);
//		httpRequest = RestAssured.given().auth().preemptive().oauth2(accessToken);
//		//		Response response1 = given().auth().preemptive().oauth2(accessToken)
//		httpRequest.contentType(ContentTyp)
//		.header("Accept", Accept)
//		.header("Authorization", "Bearer "+accessToken);
////		.header("X-Tenant", XTenant);
//
//		response = httpRequest.request(Method.GET, baseURL+serviceURL);		
//
//		Response resp = TestBase.response;
//		loggerTest.info("********************ContentType*******************");
//		loggerTest.info("Content Type==>"+resp.contentType());
//		get.contentTypeAssertion(resp);
//
//	}

			Response response1 = given().auth().preemptive().oauth2(accessToken)
					.contentType(ContentTyp)
					.header("Accept", Accept)
					.header("X-Application-Authentication", "Bearer "+accessToken)
					.header("X-Tenant", XTenant)
					.when()
					.get("https://admin-skill-edge.smartvoicehub.de/svhb/meeting/v1/ui/meetings/");
	//				.get(baseURLadmin+serviceURL);
	
			String responseBody = response1.getBody().asString();
			Iterable<io.restassured.http.Header> headerArray = response1.getHeaders();
			HashMap<String, String> allHeaders = new HashMap<String, String>();
			for(io.restassured.http.Header header: headerArray) {
				allHeaders.put(header.getName(), header.getValue());
			}
			System.out.println("All Headers-->"+ allHeaders);
			//	if (response1.getStatusCode() >= 200 && response1.getStatusCode() <= 299) {
			//		logger.info("Create Daily Feed Response = " + responseBody);
			//	} else {
			//		logger.error("Error creating daily feed = {}", responseBody);
			//	}
			System.out.println("Response is"+response1);
			String responsestring1=response1.asString();
			System.out.println(response1.getStatusCode());
			System.out.println(responsestring1);
			System.out.println(responseBody);
	}
	@DataProvider(name="GETData")
	public String[][] getAPIData() throws IOException{
		String data[][] = get.getAPIPostData("Meetings");
		return data;	
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

}
