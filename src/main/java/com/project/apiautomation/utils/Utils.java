package com.project.apiautomation.utils;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import com.jayway.jsonpath.JsonPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.PathNotFoundException;
import com.project.apiautomation.config.ER;
import com.project.apiautomation.config.PropertiesFile;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import org.apache.logging.log4j.util.PropertyFilePropertySource;
import org.json.JSONArray;
import org.json.JSONObject;

public class Utils {

	public static Map<String, String> storeValue = new HashMap<String, String>();
	public static Map<String, String> getTestData = new HashMap<String, String>();

	/************
	 * Method to get a json data into Map and iterate it to find key values pair -
	 * Please use 'getJsonDataFromBodyAsMap' method and pass jsonString as
	 * argument', 'populateMapFromJsonNode' method will be sing in
	 * 'getJsonDataFromBodyAsMap' method.
	 ************/
	private static void populateMapFromJsonNode(JsonNode jsonNode, Map<String, Object> map, String parentKey) {
		if (jsonNode.isObject()) {
			Iterator<Entry<String, JsonNode>> fields = jsonNode.fields();
			while (fields.hasNext()) {
				Entry<String, JsonNode> field = fields.next();
				String fieldName = field.getKey();
				JsonNode fieldValue = field.getValue();
				String fullKey = parentKey.isEmpty() ? fieldName : parentKey + "." + fieldName;
				populateMapFromJsonNode(fieldValue, map, fullKey);
			}
		} else if (jsonNode.isArray()) {
			for (int i = 0; i < jsonNode.size(); i++) {
				String arrayKey = parentKey + "[" + i + "]";
				populateMapFromJsonNode(jsonNode.get(i), map, arrayKey);
			}
		} else {
			map.put(parentKey, jsonNode.asText());
		}
	}

	/***
	 * Method to get a json data into Map and iterate it to find key values pair.
	 **/
	public static Map<String, Object> getJsonDataFromBodyAsMap(String jsonBodyAsString) {
		// Create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();
		// Convert JSON string to JsonNode
		JsonNode jsonNode;
		Map<String, Object> resultMap = new HashMap<>();
		try {
			jsonNode = objectMapper.readTree(jsonBodyAsString);
			// Recursively populate the map with key-value pairs
			populateMapFromJsonNode(jsonNode, resultMap, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**************************************************************************************************************************/

	/** This method is use to convert Json to json byte array **/
	public static byte[] convertJsonToByteArray(Object json) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsBytes(json);
	}

	/**
	 * This method is use to compare expected Value in actual Json response using
	 * jayway jsonpath.
	 **/
	public static void comapareExpectedJsonResponse(String jsonResponse, String expectedValue) {
		Object obj = JsonPath.read(jsonResponse, "$.*.['" + expectedValue + "']");
		System.out.println(obj.toString());
	}

	/**
	 * This method is use to get value of expected key in json response using jayway
	 * jsonpath.
	 **/
	public static List<String> getValuesForKey(String jsonString, String expectedKey) {
		try {
			// Create a generic JsonPath expression to match any key with the expectedKey
			// name
			String jsonPathExpression = "$..['" + expectedKey + "']";

			// Use JsonPath to extract the values associated with the expected key
			List<Object> values = JsonPath.read(jsonString, jsonPathExpression);

			// Convert the values to a list of strings
			List<String> stringValues = new ArrayList<>();
			for (Object value : values) {
				stringValues.add(value.toString());
			}

			// Return the list of values
			return stringValues;
		} catch (PathNotFoundException e) {
			// Handle the case where the key does not exist in the JSON
			return new ArrayList<>();
		}
	}

	/** This method is use to get value in json object based on passing key. **/
	public static List<String> getValuesInObject(JSONObject jsonObject, String key) {
		List<String> accumulatedValues = new ArrayList<>();
		for (String currentKey : jsonObject.keySet()) {
			Object value = jsonObject.get(currentKey);
			if (currentKey.equals(key)) {
				accumulatedValues.add(value.toString());
			}

			if (value instanceof JSONObject) {
				accumulatedValues.addAll(getValuesInObject((JSONObject) value, key));
			} else if (value instanceof JSONArray) {
				accumulatedValues.addAll(getValuesInArray((JSONArray) value, key));
			}
		}

		return accumulatedValues;
	}

	/** This method is use to get value in json array based on passing key. **/
	public static List<String> getValuesInArray(JSONArray jsonArray, String key) {
		List<String> accumulatedValues = new ArrayList<>();
		for (Object obj : jsonArray) {
			if (obj instanceof JSONArray) {
				accumulatedValues.addAll(getValuesInArray((JSONArray) obj, key));
			} else if (obj instanceof JSONObject) {
				accumulatedValues.addAll(getValuesInObject((JSONObject) obj, key));
			}
		}

		return accumulatedValues;
	}

}
