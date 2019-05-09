Feature:Testing for Users


  @TC-1  @regression @reqres @user
  Scenario: Create User Case
    Given Request headers
    |key|value|
    |Content-Type|application/json|
    |Accept      |application/json|
    |Content-Type|application/json|
    |Accept      |application/json|
    And Request body as json
    |key|value|
    |fileName|post-create-user.json|
    |name| randomString |
    |job | automation|
    When user hits "users" post api
    Then the status code is 201
    And Response should contain
      |key|value|
      |name |randomString|
    And get the id from the response



  @TC-2  @regression @reqres @user
  Scenario: Health check with wrong url
    Given Request headers
      |key|value|
      |Content-Type|application/json|
      |Accept      |application/json|
    And Request body as json
      |key|value|
      |fileName|post-create-user.json|
    #|name| randomString |
    #|job | automation|
    When user hits "users" post api
    Then the status code is 201
    And Response should contain
      |key|value|
      |name |abc|
    And get the id from the response


  @TC-3  @sanity @reqres
  Scenario: Health check with wrong url
    Given Request headers
      |key|value|
      |Content-Type|application/json|
      |Accept      |application/json|
    And Request body as json
      |key|value|
      |fileName|post-create-user.json|
    #|name| randomString |
    #|job | automation|
    When user hits "users" post api
    Then the status code is 201
    And Response should contain
      |key|value|
      |name |abc|
    And get the id from the response


