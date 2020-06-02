package com.qa.tests;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class GetAPITest extends TestBase{

	TestBase testbase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;

	@BeforeMethod
	public void setUP() throws ClientProtocolException, IOException {
		testbase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = serviceUrl+apiUrl;
	}

	@Test
	public void getTest() throws ClientProtocolException, IOException {

		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("bFcml2YpS9");
		authScheme.setPassword("4186a884-5883-417b-9949-c444f301f552");
		RestAssured.authentication = authScheme;

//		SessionFilter sessionFilter = new SessionFilter();
		Response responce = RestAssured.given()

				.baseUri("https://global.telekom.com/gcp-web-api/oauth") 
				.config(RestAssured.config()
						.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs("x-www-form-urlencoded",
										ContentType.URLENC)))
//				.filter(sessionFilter)
				.contentType(ContentType.URLENC.withCharset("UTF-8"))
				.header("Accept","application/json")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.formParam("grant_type", "password")
				.formParam("scope", "WMFBK4TR")
				.formParam("client_id", "bFcml2YpS9")
				.formParam("username", "balaji.balasubramanian@axxessio.com") 
				.formParam("password", "Reminder@01")
				//		.post("/auth/generatetoken/");
				.post("https://global.telekom.com/gcp-web-api/oauth");
		String s = responce.jsonPath().get("access_token");
				

		System.out.println("Response is"+responce);
		String responsestring=responce.asString();
		System.out.println(responsestring);
		System.out.println("StatusCode"+responce.getStatusCode());		
		Response response1 = given().auth().preemptive().oauth2(s)
                .contentType("application/json")
                .when()
                .get("https://admin-skill-edge.smartvoicehub.de/svhb/meeting/v1/ui/meetings/");
		String responseBody1 = response1.getBody().asString();
		System.out.println("Response is"+response1);
		String responsestring1=response1.asString();
		System.out.println(responsestring1);
		System.out.println(responseBody1);
		

				restClient = new RestClient();
				restClient.getFirstMethod(url);

	}
}
