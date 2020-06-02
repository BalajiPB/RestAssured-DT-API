package com.qa.client;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.qa.base.TestBase;
import io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AuthIBSnCVIlogin extends TestBase{

	String serviceUrl;
	String apiUrl;
	String url;
	public String accessToken;
	public String tokenType;
	public String refreshToken;
	public String scope1;
	public String token;
	
	
//	private final Logger logger = LoggerFactory.getLogger(apiUrl);

//	OAuth2Validation obj = new OAuth2Validation();
	
	public  AuthIBSnCVIlogin IBSLogin() throws JSONException {

		AuthIBSnCVIlogin obj = new AuthIBSnCVIlogin();
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("bFcml2YpS9");
		authScheme.setPassword("4186a884-5883-417b-9949-c444f301f552");
		RestAssured.authentication = authScheme;

//		logger.info("Getting OAuth Token from server - {}", "https://global.telekom.com/gcp-web-api/oauth");
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
				.post("https://global.telekom.com/gcp-web-api/oauth");

		JSONObject jsonObject = new JSONObject(responce.getBody().asString());
		accessToken = jsonObject.get("access_token").toString();
		tokenType = jsonObject.get("token_type").toString();
		refreshToken = jsonObject.get("refresh_token").toString();
		scope1 = jsonObject.get("scope").toString();
		System.out.println(responce.asString());
		System.out.println("--access--:"+accessToken);
		System.out.println("token:"+tokenType);
		System.out.println("refresh:"+refreshToken);
		System.out.println("scope:"+scope1);
//		logger.info("Oauth Token for {} with type {} is {}", "balaji.balasubramanian@axxessio.com", tokenType, accessToken);

		obj.accessToken = accessToken; 
		obj.tokenType = tokenType;
		obj.refreshToken = refreshToken;
		obj.scope1 = scope1;
		
		return obj;
	}

	public  AuthIBSnCVIlogin CVILogin() throws JSONException {
		AuthIBSnCVIlogin obj = new AuthIBSnCVIlogin();
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("apikey");
		authScheme.setPassword("b507d7ad-9e14-4a26-a3b5-0cc4ec2a2da9");
		RestAssured.authentication = authScheme;

//		logger.info("Getting OAuth Token from server - {}", "https://global.telekom.com/gcp-web-api/oauth");

		RequestSpecification httpReq = RestAssured.given()
				.header("apikey", "b507d7ad-9e14-4a26-a3b5-0cc4ec2a2da9")
//				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Connection","keep-alive")
				.header("Accept","application/json")
				.header("Content-Type", "application/json")
				.body("{\r\n" + 
						"  \"externalToken\": null,\r\n" + 
						"  \"userId\": \"120049010000000004114801\"\r\n" + 
//						"}")
						"}");
		Response responce = httpReq.request(Method.POST, "https://skill-edge.smartvoicehub.de/user/api/v1/login");

		JSONObject jsonObject = new JSONObject(responce.getBody().asString());
		token = jsonObject.get("token").toString();
		System.out.println(responce.asString());
//		System.out.println("--token--:"+token);
		System.out.println(responce.getStatusCode());
//		logger.info("Oauth Token for {} with type {} is {}", "balaji.balasubramanian@axxessio.com", tokenType, accessToken);

		obj.token = token;
		return obj;
	}
}
