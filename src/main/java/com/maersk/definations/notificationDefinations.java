package com.maersk.definations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import util.commonMethods;


public class notificationDefinations {

	private Response response;
	private String authorization;
	
	private String envType="PreProd";
	private String deviceId;

	private Properties prop;

	// Load properties once in the constructor or use a setup method
	public notificationDefinations() throws IOException {
		prop = new Properties();

		FileInputStream input = commonMethods.getPropertyFileInput(envType);

		prop.load(input);
		input.close(); //
	}

	//Feature: Validate Notification Preferences API
	//Given the API endpoint "https://api-stage.maersk.com/notifications/public/mobile/f44abee1-872d-47ff-80e2-0c74880fcdf9/preferences" is available
	//Scenario: Validate the Notification Preferences API for valid device ID

	@Given("a authoriztionId and device ID {string}")
	public void setAuthorizationDeviceID_NotificationsPreferencesAPI(String deviceId1) {
		
		authorization = commonMethods.getrefreshTokenId(prop); 
		deviceId=deviceId1;

	}
	@When("I send a GET request to Notification Preferences API endpoint")
	public void sendGetRequest_NotificationsPreferencesAPI() {

		response = commonMethods.getRequestPreferencesAPI(prop,deviceId,authorization);
		
		System.out.println("Response Status: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asPrettyString());

	}

	@Then("the response status code of Notification Preferences API request should be {string}")
	public void verifyStatusCode_NotificationsPreferencesAPI(String StatusCode1) {
		response.then().statusCode(Integer.parseInt(StatusCode1));
	}

	@Then("the response body should contain the user's notification preferences")
	public void verifyResponse_NotificationsPreferencesAPI() {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Response Body: " + response.getBody().asPrettyString());
	}


	//Scenario: Validate the Notification Preferences API for an invalid device IDD

	//Scenario: Validate the response for an unauthorized user


	@Given("a invalid authoriztionId and device ID {string} exists in the system")
	public void setInvalidAuthorizationDeviceID_NotificationsPreferencesAPI(String deviceId1) {
		authorization = prop.getProperty("invalidAuthorizationToken");
		deviceId=deviceId1;

	}
	


	
}
