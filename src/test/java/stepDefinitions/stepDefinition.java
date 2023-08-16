package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class stepDefinition extends Utils{
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	static String place_id;
	TestDataBuild data = new TestDataBuild();
	 
	 @Given("Add Place Payload with {string} {string} {string}")
	 public void add_place_payload_with(String name, String language, String address) throws IOException {
		 
		res=given().spec(requestSpecifications())
		.body(data.addPlacePayload(name,language,address));
	}

	 @When("User calls {string} with {string} http request")
	 public void user_calls_with_http_request(String resource, String method)
	{
		APIResources resourceAPI = APIResources.valueOf(resource);
		
		resspec=new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200)
				.build();
		if(method.equalsIgnoreCase("POST"))
		response=res.when().post(resourceAPI.getResource());
		
		else if(method.equalsIgnoreCase("GET"))
			response=res.when().get(resourceAPI.getResource());
		
	
	}

	@Then("the API call is success with status code as {int}")
	public void the_API_call_is_success_with_status_code_as(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(response.getStatusCode(),200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {
	    // Write code here that turns the phrase above into concrete actions
	   
	    assertEquals(getJsonPath(response,keyValue),ExpectedValue);
	}


    @Then("verify place id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedname, String resource) throws IOException {
    	place_id=getJsonPath(response,"place_id");
    	res=given().spec(requestSpecifications()).queryParam("place_id", place_id);
    	user_calls_with_http_request(resource, "GET");
    	String actualname=getJsonPath(response,"name");
    	assertEquals(actualname,expectedname);
    	
    }
    
    @Given("DeletPlace Payload")
    public void delet_place_payload() throws IOException {
        // Write code here that turns the phrase above into concrete actions
    	res=given().spec(requestSpecifications()).body(data.deleteplacePayload(place_id));
    }



}
