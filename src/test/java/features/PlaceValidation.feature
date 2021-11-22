Feature: Validation add place API

  @AddPlace
  Scenario Outline: Verify if place is being successfully added using add place API
    Given Add place payload "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the API call is success with status code is 200
    And "status" in response body should be "OK"
    And "scope" in response body should be "APP"
    And verify place_Id created maps to "<name>" using "GetPlaceAPI"

    Examples:
      | name   | language | address |
      | Syed   | Arabic   | Saudi   |
#      | B name | English  | B house |


  @DeletePlace
  Scenario: Verify if delete place functionality is working
    Given Delete place payload
    When user calls "DeletePlaceAPI" with "POST" http request
    Then the API call is success with status code is 200
    And "status" in response body should be "OK"