Feature: Authentication External Login API
 I want to test Authentication External Login API
 
#Given the Authentication External Login API endpoint https://api-stage.maersk.com/shipmentmobile/security/authentication/external

  Scenario Outline: Validate Authentication External Login API with valid credentials for multiple user
    Given I enter username as "<username>" and password as "<password>"
    When I send a POST request for Authentication External Login API with given credentials
    Then I verify the status as "<statusCode>" for given credentials
    And I verify the result for Authentication External Login API as "<Result>" with given credentials
    And I want to get token_id from response of Authentication External Login API with given credentials
 
    Examples:
      | username      | password          | statusCode | Result  |
      | mob_user_2 		| Maersk@6 			    |        200 | SUCCESS |
      | mob-3 			  | Maersk@13			    |        200 | SUCCESS |

  
  Scenario Outline: Validate  Authentication External Login API with invalid credentials	
   	Given I enter username as "<username>" and password as "<password>"
    When I send a POST request for Authentication External Login API with given credentials
   	Then I verify the status as "<statusCode>" for given credentials
    And I verify the message for Authentication External Login API as "<Message>" with given credentials

    Examples:
      | username          | password            | statusCode | Message  			    |
      | mob_user_2 		    | Maersk@7 			      |        401 | Invalid password 	|
      | mob_user_21		    | Maersk@7 			      |        401 | Invalid username		|
      | 				          | Maersk@7 			      |        400 | Validation errors	|
      | mob_user_2		    | 		 			          |        400 | Validation errors	|
      | 				          | 		 			          |        400 | Validation errors	|
      
   
   Scenario Outline: Validate Authentication External Login API with valid credentials for single user
   
    Given I enter username as "<username>" and password as "<password>"
    When I send a POST request for Authentication External Login API with given credentials
    Then I verify the status as "<statusCode>" for given credentials
    And I want to get token_id from response of Authentication External Login API with given credentials
  
     Examples:
      | username          | password          	| statusCode	|
      | mob_user_2 		    | Maersk@6			      | 200			    |
 
 
#Feature: Refresh Token Login API
#I want to test Refresh Token Login API
#Given the Authentication External Login API endpoint https://api-stage.maersk.com/shipmentmobile/security/tokens/external

  Scenario: Validate Refresh Token Login API for successful token generation with a valid CustomerCode
    Given I have a valid token_id and CustomerCode as "<VALID_CUSTOMER_CODE>"
    When I send a POST request for Refresh Token Login API endpoint with token_id and the CustomerCode in the request body
    Then I verify the status as "<statusCode>" for given credentials
    And I want to get id_token from response of Refresh Token Login API with given credentials
  
  Examples:
      | VALID_CUSTOMER_CODE         | statusCode	|
      | 10000007951 		  		      | 200			    |   