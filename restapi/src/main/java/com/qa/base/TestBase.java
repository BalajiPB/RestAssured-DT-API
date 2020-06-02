package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {

	public Properties prop;
	public static RequestSpecification httpRequest;
	public static Response response;
	public Logger logger;

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest loggerTest;
	
	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setupLog() {
		htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"/test-output/extent.html"));
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		
		logger =LogManager.getLogger("restapi");
		//	Logger.getLogger("restapi");//added Logger
		PropertyConfigurator.configure("Log4j.properties"); //added logger
		logger.setLevel(Level.DEBUG);
		
	}


}
