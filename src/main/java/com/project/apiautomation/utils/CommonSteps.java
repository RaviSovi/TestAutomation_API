package com.project.apiautomation.utils;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

import com.project.apiautomation.base.RequestBuilder;
import com.project.apiautomation.config.ER;
import com.project.apiautomation.config.PropertiesFile;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class CommonSteps {
	
	public static Response response;
	
	public static void userShouldGetTheResponseCode(Response response, Integer responseStatusCode) throws Exception {
		try {
			assertEquals(Long.valueOf(responseStatusCode), Long.valueOf(response.getStatusCode()));
			ER.Pass("User get a response code : " + Long.valueOf(response.getStatusCode()));
		} catch (Exception e) {
			ER.Fail("User get a response code : " + Long.valueOf(response.getStatusCode()));
		}
	}
	
	public static void userHasAccessToEndpoint(String endpoint) {		
		Utils.storeValue.put("endpoint", endpoint);
		ER.Pass("User has access to endpoint : "+Utils.storeValue.get("endpoint"));
	}
	
	public static void userValidatesTheResponseWithJSONSchemaFromExcel(Response response, String responseDataFromExcel) {
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(responseDataFromExcel));
		ER.Pass("Successfully Validated Jason schema from Excel");
	}
	
	public static void userValidatesResponseWithJSONSchema(Response response, String schemaFileName) {
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("app1/schemas/"+schemaFileName));
		ER.Pass("Successfully Validated schema from "+schemaFileName);
	}
	
	public static String userCreatesAAuthTokenWithCredential(String username, String password) throws Exception {
		JSONObject credentials = new JSONObject();
		credentials.put("username", username);
		credentials.put("password", password);
		
		Map<String, String> headers = new HashMap<>();
	    headers.put("Content-Type", "application/json");
		
		response = RequestBuilder.sendRequest(PropertiesFile.getProperty("app1.baseURL"), PropertiesFile.getProperty("app1.addBook.endPoint"), headers, null, credentials.toString(), "POST");
		String token = response.path("token");
		ER.Info("Auth Token: "+token);
		//Utils.storeValue.put("token", "token="+token);
		return token;
	}
}
