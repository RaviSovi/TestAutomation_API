package com.project.apiautomation.config;

import java.io.IOException;
import java.util.logging.Logger;
import com.aventstack.extentreports.Status;

public class ER {
	public static Logger logger = Logger.getLogger("ER");

	/** This method will log the status of the test step along with message in extent report and console**/
	public static void Pass(String msg)
	{
		ExtentTestManager.getTest().log(Status.PASS, msg);
		logger.info("***********************************************************************************************");
		logger.info(msg);
		logger.info("***********************************************************************************************");
	}
	
	/** This method will log the status of the test step along with message in extent report and console**/
	public static void Info(String msg)
	{
		ExtentTestManager.getTest().log(Status.INFO, msg);
		logger.info("***********************************************************************************************");
		logger.info(msg);
		logger.info("***********************************************************************************************");
	}
	
	/** This method will log the status of the test step along with message in extent report and console**/
//	public static void Info(String msg, MediaEntityModelProvider provider)
//	{
//		ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, msg);
//		logger.info("***********************************************************************************************");
//		logger.info(msg);
//		logger.info("***********************************************************************************************");
//	}
	
	/** This method will log the status of the test step along with message in extent report and console**/
	public static void Warning(String msg)
	{
		ExtentTestManager.getTest().log(Status.WARNING, msg);
		logger.info("***********************************************************************************************");
		logger.info(msg);
		logger.info("***********************************************************************************************");
	}
	
	/** This method will log the status of the test step along with message in extent report and console**/
	public static void Fail(String msg) throws IOException, Exception
	{
		ExtentTestManager.getTest().log(Status.FAIL, msg);
		logger.info("***********************************************************************************************");
		logger.info(msg);
		logger.info("***********************************************************************************************");
		throw new Exception(msg);
	}
}
