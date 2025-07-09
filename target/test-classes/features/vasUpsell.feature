Feature: Free time API
  As a user of the Maersk API
  I want to retrieve free time information for containers
  So that I can track container availability

   #Given the base API URL is "https://api-stage.maersk.com/vas-experience/dnd"

   Scenario: Submit free time information with valid authorization and valid container number
    Given I have a authoriztionId and container number "MOBILE101"
    When I send an POST request to DND free time API with container number
    Then the response status code of DND free time API should be "200"
    And the response should indicate successful creation for the given container number
    
    
   Scenario:Submit free time information with valid authorization and invalid container number
    Given I have a authoriztionId and container number "INVALID123"
    When I send an POST request to DND free time API with container number
    Then the response status code of DND free time API should be "400"
     
   Scenario: Submit free time information with invalid authorization and container number
    Given I have a invalid authoriztionId and container number "MOBILE101"
    When I send an POST request to DND free time API with container number
    Then the response status code of DND free time API should be "401"
    
    Scenario: Submit free time information with valid authorization and valid container number for multiple data
    Given I have a authoriztionId and container number "<container_id>"
    When I send an POST request to DND free time API with container number
    Then the response status code of DND free time API should be "<status_code>"
 	
 	Examples:
      | container_id | status_code |
      | MOBILE101    | 200         |
      | MOBILE102    | 403         | 
      | UNKNOWN123   | 400         |