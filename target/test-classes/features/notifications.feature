Feature: Notifications API
  I want to test notifications APIs
 
#Given the Notification Preferences API endpoint "https://api-stage.maersk.com/notifications/public/mobile/f44abee1-872d-47ff-80e2-0c74880fcdf9/preferences"

	Scenario: Validate the Notification Preferences API for valid device ID
    Given a authoriztionId and device ID "<deviceID>"
    When I send a GET request to Notification Preferences API endpoint
    Then the response status code of Notification Preferences API request should be "200"
    And the response body should contain the user's notification preferences
   
   Examples:
      |	deviceID	 								|
      | f44abee1-872d-47ff-80e2-0c74880fcdf9        |
      
      
   	Scenario: Validate the Notification Preferences API for an invalid device ID
    Given a authoriztionId and device ID "<deviceID>"
    When I send a GET request to Notification Preferences API endpoint
    Then the response status code of Notification Preferences API request should be "404"
    
       Examples:
      |	deviceID	 								|
      | f44abee1-872d-80e2-0c74880fcdf9        		|
      |  											|
      
    
    Scenario: Validate the Notification Preferences API for an unauthorized user
    Given a invalid authoriztionId and device ID "<deviceID>" exists in the system
    When I send a GET request to Notification Preferences API endpoint
    Then the response status code of Notification Preferences API request should be "401"
    
    Examples:
      |	deviceID	 								|
      | f44abee1-872d-47ff-80e2-0c74880fcdf9        |
       
  