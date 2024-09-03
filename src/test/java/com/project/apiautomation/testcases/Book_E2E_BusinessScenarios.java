package com.project.apiautomation.testcases;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.project.apiautomation.base.RequestBuilder;
import com.project.apiautomation.config.ER;
import com.project.apiautomation.config.JsonReader;
import com.project.apiautomation.config.PropertiesFile;
import com.project.apiautomation.utils.CommonSteps;
import com.project.apiautomation.utils.JsonUtil;
import com.project.apiautomation.utils.Utils;
import io.restassured.response.Response;

public class Book_E2E_BusinessScenarios extends PropertiesFile
{
	@BeforeMethod (alwaysRun = true)
	public void initilization() {
		System.out.println("insite initialize method");
		new RequestBuilder();
	}



	@Test(priority=1)
    public void addBookBusinessScenario() throws Exception
    {
		 //Map<String, String> headers = new HashMap<>();
	     //headers.put("Content-Type", "application/json");
		
		
    	ER.Info("Execution Started.");
    	CommonSteps.userHasAccessToEndpoint(PropertiesFile.getProperty("app1.addBook.endPoint"));
    	//requestbuilder.response = requestbuilder.requestSetup("app1").body(JsonReader.getRequestBody("requestBody.json","addBook_TC01")).when().post(Utils.storeValue.get("endpoint").toString());
    	
    	Response resp = RequestBuilder.sendRequest(PropertiesFile.getProperty("app1.baseURL"), PropertiesFile.getProperty("app1.addBook.endPoint"), null, null, JsonReader.getRequestBody("requestBody.json","addBook_TC01"), "POST");
    	
    	
    	CommonSteps.userShouldGetTheResponseCode(resp, 200);
        //assertTrue( true );
    	String respString = resp.getBody().asString();
    	String id = Utils.getValuesForKey(respString, "ID").get(0);
    	Utils.storeValue.put("ID", id);
    	System.out.println("**********"+Utils.storeValue.get("ID"));
    	
    	JsonUtil.getJsonDifferences(JsonReader.getRequestBody("expectedresponseBody.json","addBook_TC01"), respString);
    	CommonSteps.userValidatesResponseWithJSONSchema(resp, "addBookSchema.json");
    }
	
	@Test(priority=2)
    public void getBookBusinessScenario() throws Exception
    {
		 //Map<String, String> headers = new HashMap<>();
		 Map<String, String> param = new HashMap<>();
	     //headers.put("Content-Type", "application/json");
	     param.put("ID", Utils.storeValue.get("ID"));
		
		
    	ER.Info("Execution Started.");
    	CommonSteps.userHasAccessToEndpoint(PropertiesFile.getProperty("app1.getBook.endPoint"));
    	//requestbuilder.response = requestbuilder.requestSetup("app1").body(JsonReader.getRequestBody("requestBody.json","addBook_TC01")).when().post(Utils.storeValue.get("endpoint").toString());
    	
    	Response resp = RequestBuilder.sendRequest(PropertiesFile.getProperty("app1.baseURL"), PropertiesFile.getProperty("app1.getBook.endPoint"), null, param, null, "GET");
    	
    	CommonSteps.userShouldGetTheResponseCode(resp, 200);
    	
    	String respString = resp.getBody().asString();
    	JsonUtil.getJsonDifferences(JsonReader.getRequestBody("expectedresponseBody.json","getBook_TC01"), respString);
    }
	
	@Test(priority=3)
    public void deleteBookBusinessScenario() throws Exception
    {
//		 Map<String, String> headers = new HashMap<>();
//	     headers.put("Content-Type", "application/json");
		
		
    	ER.Info("Execution Started.");
    	CommonSteps.userHasAccessToEndpoint(PropertiesFile.getProperty("app1.deleteBook.endPoint"));
    	//requestbuilder.response = requestbuilder.requestSetup("app1").body(JsonReader.getRequestBody("requestBody.json","addBook_TC01")).when().post(Utils.storeValue.get("endpoint").toString());
    	
    	Response resp = RequestBuilder.sendRequest(PropertiesFile.getProperty("app1.baseURL"), PropertiesFile.getProperty("app1.deleteBook.endPoint"), null, null, JsonReader.getRequestBody("requestBody.json","deleteBook_TC01"), "GET");
    	
    	
    	CommonSteps.userShouldGetTheResponseCode(resp, 200);
    	
    	String respString = resp.getBody().asString();
    	JsonUtil.getJsonDifferences(JsonReader.getRequestBody("expectedresponseBody.json","deleteBook_TC01"), respString);
    }
}
