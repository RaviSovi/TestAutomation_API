package com.project.apiautomation.base;

import java.util.Map;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import com.project.apiautomation.config.PropertiesFile;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import io.restassured.builder.RequestSpecBuilder;

public class RequestBuilder {

//	public Response response;
//	private static String CONTENT_TYPE;
//
//	public RequestSpecification requestSetup(String appBaseURL) throws Exception {
//		CONTENT_TYPE = PropertiesFile.getProperty("content.type");
//		RestAssured.reset();
//		Options options = Options.builder().logStacktrace().build();
//		RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options);
//		if (appBaseURL.equalsIgnoreCase("app1")) {
//			System.out.println("Base URL 1 called");
//			RestAssured.baseURI = PropertiesFile.getProperty("app1.baseURL");
//			System.out.println(RestAssured.baseURI);
//		} else if (appBaseURL.equalsIgnoreCase("app2")) {
//			System.out.println("Base URL 2 called");
//			RestAssured.baseURI = PropertiesFile.getProperty("app2.baseURL");
//		} else if (appBaseURL.equalsIgnoreCase("app3")) {
//			System.out.println("Base URL 3 called");
//			RestAssured.baseURI = PropertiesFile.getProperty("app3.baseURL");
//		} else {
//			System.out.println("Base URL 4 called");
//			RestAssured.baseURI = PropertiesFile.getProperty("app4.baseURL");
//		}
//		return RestAssured.given()
//				.config(config)
//				.filter(new RestAssuredRequestFilter())				
//				.contentType(CONTENT_TYPE)
//				.accept(CONTENT_TYPE);
//	}
	
/***********************************************************************************************************************/
	
	/** This method use to send request based on request type and pass parameters into that**/

	public static Response sendRequest(String baseUrl, String endpoint, Map<String, String> headers,
			Map<String, String> parameters, String jsonBody, String method) {
		RequestSpecBuilder builder = new RequestSpecBuilder().setBaseUri(baseUrl).setBasePath(endpoint).setContentType(ContentType.JSON)
				.addFilter(new RestAssuredRequestFilter());

		if (headers != null) {
			builder.addHeaders(headers);
		}

		if (parameters != null) {
			builder.addParams(parameters);
		}

		RequestSpecification requestSpec = builder.build();

		// Perform the request based on the provided HTTP method
		Response response;
		switch (method.toUpperCase()) {
		case "POST":
			response = RestAssured.given(requestSpec).body(jsonBody != null ? jsonBody : "").post();
			break;
		case "PUT":
			response = RestAssured.given(requestSpec).body(jsonBody != null ? jsonBody : "").put();
			break;
		case "DELETE":
			response = RestAssured.given(requestSpec).body(jsonBody != null ? jsonBody : "").delete();
			break;
		case "PATCH":
			response = RestAssured.given(requestSpec).body(jsonBody != null ? jsonBody : "").patch();
			break;
		case "GET":
		default:
			response = RestAssured.given(requestSpec).body(jsonBody != null ? jsonBody : "").get();
			break;
		}

		return response;
	}

}
