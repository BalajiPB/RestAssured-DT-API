package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.client.AuthIBSnCVIlogin;
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

public class IBSGet extends TestBase{

	TestBase testbase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;

	private final Logger logger = LoggerFactory.getLogger(apiUrl);

	@BeforeMethod
	public void setUP() throws ClientProtocolException, IOException {
		testbase = new TestBase();

		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = serviceUrl+apiUrl;
	}

	@Test
	public void getTest() throws ClientProtocolException, IOException {

		AuthIBSnCVIlogin auth = new AuthIBSnCVIlogin();
		auth.IBSLogin();
		
		String accessToken = auth.accessToken;
		String tokenType = auth.tokenType;
		String refreshToken = auth.refreshToken;
		String scope = auth.scope1;
			Response response1 = given().auth().preemptive().oauth2(accessToken)
				.contentType("application/json")
				.header("Accept","application/json")
				.header("X-Application-Authentication", "Bearer "+accessToken)
				.header("X-Tenant", "GLOBAL")
//                .formParam("token_type", tokenType)
//                .formParam("refresh_token", refreshToken)
//                .formParam("scope", scope)
				.when()
//				.get("https://admin-skill-edge.smartvoicehub.de/svhb/meeting/v1/ui/meetings/");
				.get("https://admin-skill-edge.smartvoicehub.de/svhb/meeting/v1/ui/transcript/?meetingId=3");
		
		String responseBody = response1.getBody().asString();
		Iterable<io.restassured.http.Header> headerArray = response1.getHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for(io.restassured.http.Header header: headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("All Headers-->"+ allHeaders);
		if (response1.getStatusCode() >= 200 && response1.getStatusCode() <= 299) {
			logger.info("Create Daily Feed Response = " + responseBody);
		} else {
			logger.error("Error creating daily feed = {}", responseBody);
		}

				System.out.println("Response is"+response1);
				String responsestring1=response1.asString();
				System.out.println(response1.getStatusCode());
				System.out.println(responsestring1);
				System.out.println(responseBody);
	}
}
