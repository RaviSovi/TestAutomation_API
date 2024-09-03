package com.project.apiautomation.config;

import java.io.File;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	public static ExtentReports extent;
	public static String reportFileName = "AutomationReport"+".html";
	public static String fileSeparator = System.getProperty("file.separator");
	public static String reportFilePath = System.getProperty("user.dir")+fileSeparator+"TestReports";
	public static String reportFileLocation = reportFilePath+fileSeparator+reportFileName;
	
	/** Get an extent report instance **/
	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance();
		return extent;
	}
	
	/** Create the report path **/
	public static String getReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory : '" + path + "' is created");
				return reportFileLocation;
			} else {
				System.out.println("Failed to create directory at : '" + path);
				return System.getProperty("user.dir");
			}
		} else {
			System.out.println("Directory already exists at : '" + path);
		}
		return reportFileLocation;
	}
	
	/** Create an extent report instance **/
	public static ExtentReports createInstance()
	{
		String fileName = getReportPath(reportFilePath);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle(reportFileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(reportFileName);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("AUT", "QA");
		
		return extent;
	}
}
