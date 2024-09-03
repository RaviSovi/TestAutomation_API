package com.project.apiautomation.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {

	private static String dataPath;
	private static JSONParser parser = new JSONParser();
	private static Object body;

	public static String getRequestBody(String jsonFileName, String jsonKey) throws Exception {		
		try {
			dataPath= new File(PropertiesFile.getProperty("test.data.path")).getAbsolutePath()+File.separator;
			System.out.println(dataPath);
			body = ((JSONObject)parser.parse(new FileReader(dataPath+jsonFileName))).get(jsonKey);
			System.out.println(body);
			if (body == null) {
				throw new RuntimeException("NO DATA FOUND in JSON file '" + jsonFileName +"' for key '"+jsonKey+"'");
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("JSON file not found at path: " + dataPath+jsonFileName);
		} catch (IOException e) {
			throw new RuntimeException("IOException while reading file: " + jsonFileName);
		} catch (ParseException e) {
			throw new RuntimeException("Parse Exception occured while Parsing: " + jsonFileName);
		}
		return body.toString();
	}
}
