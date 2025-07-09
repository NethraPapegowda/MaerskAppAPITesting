package com.maersk.definations;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.commonMethods;
import util.scenerioContext;

import org.testng.Assert;

import static org.hamcrest.Matchers.equalTo;

public class externalLoginDefinations {
	private String username;
	private String password;
	private Response response;
	private String token_id;
	private Properties prop;
	private String customerCode;
	private String authorization;

	private String envType="PreProd";


	// Load properties once in the constructor or use a setup method
	public externalLoginDefinations() throws IOException {
		prop = new Properties();

		FileInputStream input = commonMethods.getPropertyFileInput(envType);

		prop.load(input);
		input.close(); // Close file input stream
	}

	@Given("I enter username as {string} and password as {string}")
	public void setCredentials_AuthenticationExternalLoginAPI(String usernameKey, String passwordKey) {
		username = usernameKey;
		password = passwordKey;
	}

	@When("I send a POST request for Authentication External Login API with given credentials")
	public void sendPostRequest_AuthenticationExternalLoginAPI() {

		response= commonMethods.postRequestAuthenticationExternalAPI(prop,username,password);

		System.out.println("Response Status: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asPrettyString());
	}

	@Then("I verify the status as {string} for given credentials")
	public void verifyStatusCode(String statusCode) {
		response.then().statusCode(Integer.parseInt(statusCode));
	}

	@Then("I verify the result for Authentication External Login API as {string} with given credentials")
	public void verifyResult_AuthenticationExternalLoginAPI(String expectedResult) {
		response.then().body("result", equalTo(expectedResult));
	}

	@Then("I want to get token_id from response of Authentication External Login API with given credentials")
	public void getTokenId_AuthenticationExternalLoginAPI() {
		token_id = response.jsonPath().getString("tokenId");
		scenerioContext.setTokenId(token_id);
		System.out.println("***************"+ token_id);

		Assert.assertNotNull("Token ID should not be null", token_id);
	}

	//For invalid scenario
	@Then("I verify the message for Authentication External Login API as {string} with given credentials")
	public void verifyMessage(String expectedResult) {
		response.then().body("message", equalTo(expectedResult));

		JsonPath js = new JsonPath(response.asString());

		int stsCode = response.getStatusCode();
		if(stsCode == 400)
		{
			String subError = js.get("subErrors[0].message");
			String fieldName = js.get("subErrors[0].field"); 
			System.out.println("********************** field : " +fieldName + " "+ subError );
		}

	}


	//Feature: External Token Generation API
	//Given the API endpoint "https://api-stage.maersk.com/shipmentmobile/security/tokens/external" is available

	@Given("I have a valid token_id and CustomerCode as {string}")
	public void settokenIdCustomerCode_ExternalTokenGenerationAPI(String customerCode1) {
		// Write code here that turns the phrase above into concrete actions

		token_id =  scenerioContext.getTokenId();
		customerCode = customerCode1;

	}

	@When("I send a POST request for Refresh Token Login API endpoint with token_id and the CustomerCode in the request body")
	public void sendPostRequest_ExternalTokenGenerationAPI(){
		// Write code here that turns the phrase above into concrete actions

		response= commonMethods.postRequestRefreshTokenAPI(prop,token_id,customerCode);

		// Log the response for debugging
		System.out.println("Response Status: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asPrettyString());

	}

	@Then("I want to get id_token from response of Refresh Token Login API with given credentials")
	public void getIdToken_ExternalTokenGenerationAPI() {
		// Write code here that turns the phrase above into concrete actions

		authorization = response.jsonPath().getString("idToken");
		scenerioContext.setTokenId(authorization);
		System.out.println("***************"+ authorization);

		Assert.assertNotNull("Token ID should not be null", authorization);
	}

}
