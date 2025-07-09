package com.maersk.definations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import util.commonMethods;

public class vasUpsellDefinations {

	private Response response;
	private String authorization;
	private String shipmentnumber;

	private String envType="PreProd";

	private Properties prop;

	// Load properties once in the constructor or use a setup method
	public vasUpsellDefinations() throws IOException {
		prop = new Properties();

		FileInputStream input = commonMethods.getPropertyFileInput(envType);

		prop.load(input);
		input.close(); //
	}


	//Feature: Validate Free time API 
	//Given the API endpoint "https://api-stage.maersk.com/vas-experience/dnd" is available
	//Scenario: Submit free time information with valid authorization and valid container number

	@Given("I have a authoriztionId and container number {string}")
	public void setAuthorizationShipmentNumber_DnDFreeTimeAPI(String shipmentnumber1) {
		
		authorization = commonMethods.getrefreshTokenId(prop); 
		shipmentnumber=shipmentnumber1;
	}

	@When("I send an POST request to DND free time API with container number")
	public void sendPostRequest_DnDFreeTimeAPI() {

		response = commonMethods.postRequestDnDFreeTimeAPI(prop, shipmentnumber, authorization);
		
		//System.out.println("Response Status: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asPrettyString());

	}

	@Then("the response status code of DND free time API should be {string}")
	public void verifyStatusCode_DnDFreeTimeAPI(String StatusCode) {

		int expectedStatus = Integer.parseInt(StatusCode);

		System.out.println("*************************************************");
		
		System.out.println("Response Status: " + response.then().statusCode(expectedStatus));
		
		System.out.println("*************************************************");


	}

	@Then("the response should indicate successful creation for the given container number")
	public void verifyResponse_DnDFreeTimeAPI() {

		// Log full response body for debugging
		String responseBody = response.getBody().asPrettyString();
		System.out.println("Response Body: " + responseBody);


	}


	//Scenario:Submit free time information with valid authorization and invalid container number

	//Scenario: Submit free time information with invalid authorization and container number

	@Given("I have a invalid authoriztionId and container number {string}")
	public void setInvalidAuthorizationShipmentNumber_DnDFreeTimeAPI(String shipmentnumber1) {


		// Set up test data
		authorization = prop.getProperty("invalidAuthorizationToken");
		shipmentnumber=shipmentnumber1;

	}

	//Scenario: Submit free time information with valid authorization and valid container number for multiple data
}
