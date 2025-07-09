Feature: Validate internal user login feature
  I want to test internal user login functionality using this feature

  @InternalLogin
  Scenario Outline: Validate internal login functionality with Azure AD providing valid oidcid token
    Given As a internal user I want to get oidcId token from external source
    When I set careerCode as "<carrierCode>" and "<oidcid>" token
    Then I verify the status as "<statusCode>"
    And I verify the result as "<Result>"
    And I want to get token_id from response

    @InternalLogin
    Examples:
      | carrierCode | oidcid | statusCode | Result  |
      | MAEU        | valid  |        200 | SUCCESS |