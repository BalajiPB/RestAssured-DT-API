package com.qa.client;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import org.testng.Assert;

import com.qa.base.TestBase;
import com.qa.xlsData.XLSUtils;

public class CVIPostGeneral extends TestBase{

	public String[][] xlsData;

	//	public void postMethodforCVIP(String token, String ContentTyp, String Accept, String ApiKey, String payload, String baseURL, 
	public Response postMethodforCVIP(String token, String ContentTyp, String Accept, String ApiKey, String payload, String baseURL,		
			String serviceURL){

		//Request Object
		httpRequest = RestAssured.given().auth().preemptive().oauth2(token);
		httpRequest.header("Authorization", "Bearer "+token);
		httpRequest.header("Content-Type", ContentTyp);
		httpRequest.header("Accept", Accept);
		httpRequest.header("apikey", ApiKey);
		httpRequest.body(payload);

		response = httpRequest.request(Method.POST, baseURL+serviceURL);

		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusCode());

		return response;
	}


	public String [][] getAPIPostData(String SheetName) throws IOException{

//		XLSUtils xls = new XLSUtils();
//		AuthIBSnCVIlogin auth = new AuthIBSnCVIlogin();

		String path = System.getProperty("user.dir")+"/src/test/java/com/qa/xlsData/TestData.xlsx";

		int rownum = XLSUtils.getRowCount(path, SheetName);
		int colcount = XLSUtils.getCellCount(path, SheetName, 1); 

		String xlsData[][] = new String[rownum][colcount];

		for(int i=1; i<= rownum; i++) {
			for(int j=0; j< colcount; j++) {
				xlsData[i-1][j] = XLSUtils.getCellData(path, SheetName, i, j);
				//				System.out.println(xlsData[i-1][j]);
			}
		}

		return xlsData;	
	}

	public String responseBodyAssertion(Response response) {//String responseBody) {
//		logger.info("********************Resopnse Body*******************");
		String responseBody = response.getBody().asString();
//		logger.info("Response Body==>"+responseBody);
		
		try {
			Assert.assertTrue(responseBody!= null);
			System.out.println("TRUE");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseBody;
	}

	public void statusCodeAssertion(Response response) {
//		logger.info("********************Status Code*******************");
		int statusCode = response.getStatusCode();
//		logger.info("Status Code==>"+statusCode);
//		try {
			Assert.assertEquals(statusCode, 200);
			
//			System.out.println("TRUE");
//		} catch (Exception e) {
//			 TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	public void responseTimeAssertion(Response response) {
		//		logger.info("********************Response Time*******************");
				long responseTime = response.getTime();
		//		logger.info("Response Time==>"+responseTime);
		Assert.assertTrue(responseTime<2000);
	}
	//status line
	public void statusLineAssertion() {
		logger.info("********************Status Line*******************");
		String statusLine = response.getStatusLine();
		logger.info("Status Line==>"+statusLine);
		Assert.assertEquals(statusLine, "");
	}

	//check Content Type
	public void contentTypeAssertion(Response response) {
//		logger.info("********************ContentType*******************");
		String contentType = response.header("Content-Type");
//		logger.info("Content Type==>"+contentType);
		try {
			Assert.assertEquals(contentType, "application/json");
			System.out.println("TRUE");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//check Server Type>>>>>>
	public void serverTypeAssertion() {
		logger.info("********************ServerType*******************");

		String serverType = response.header("Server");
		logger.info("Server Type==>"+serverType);
		Assert.assertEquals(serverType, "");
	}

	//check Server Type
	public void contentEncodingAssertion() {
		logger.info("********************Content Encoding*******************");
		String contentEncoding = response.header("Content-Encoding");
		logger.info("Content Encoding==>"+contentEncoding);
		Assert.assertEquals(contentEncoding, "");
	}

	//check Content Encoding
	public void contentLengthAssertion() {
		logger.info("********************Content Length*******************");
		String contentLen = response.header("Content-Length");
		logger.info("Content Type==>"+contentLen);
		Assert.assertEquals(contentLen, "");
	}

	//check Cookies
	public void cookiesAssertion() {
		logger.info("********************Cookies*******************");
		String cookies = response.getCookie("");
		logger.info("Content Type==>"+cookies);
		Assert.assertEquals(cookies, "");
	}


}
