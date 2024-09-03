package com.project.apiautomation.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class PropertiesFile {

	private static final Logger LOG = LogManager.getLogger(PropertiesFile.class);
	public static Properties pro;
	public static File f;
	public static String appname;

	@BeforeMethod(alwaysRun = true)
	@Parameters("appName")
	public static Properties loadProperties(String a) throws Exception {
		appname =a;
		//Utils.storeValue.put(appname, a);
		System.out.println("????????????????"+appname);
		if (pro == null) {
			pro = new Properties();
			if (appname.equalsIgnoreCase("app1")) {
				f = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\");
				System.out.println("**** App 1 API Automation proerties file executed.");
			} else if (appname.equalsIgnoreCase("app2")) {
				f = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\");
				System.out.println("**** App 2 API Automation proerties file executed.");
			}

			File[] file = f.listFiles();
			for (int i = 0; i < file.length; i++) {
				pro.load(new FileInputStream(file[i]));
			}
		}
		return pro;
	}

	public static String getProperty(String prop) throws Exception {
		pro = loadProperties(appname);
		return pro.getProperty(prop);
	}
}
