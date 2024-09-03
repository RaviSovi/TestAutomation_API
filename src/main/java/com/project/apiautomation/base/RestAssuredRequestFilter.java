package com.project.apiautomation.base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.project.apiautomation.config.ER;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class RestAssuredRequestFilter implements Filter {
	private static final Logger LOG = LogManager.getLogger(RestAssuredRequestFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        ER.Info(" Request Method => " + requestSpec.getMethod() + 
        		"<br> Request URI => " + requestSpec.getURI() + 
        		"<br> Request Header =>\n" + requestSpec.getHeaders() +
        		"<br> Request Body => " + requestSpec.getBody() +
        		"<br>---------------------------------------------------------------------------------------------------"+
        		"<br> Response Status => "+ response.getStatusLine()+ 
        		"<br> Response Header =>\n"+ response.getHeaders()+ 
        		"<br> Response Body => " + response.getBody().prettyPrint());
        return response;
    }
}