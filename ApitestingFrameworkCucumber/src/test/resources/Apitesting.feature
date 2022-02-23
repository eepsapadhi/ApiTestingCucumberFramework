Feature: API Testing for Create Add Update Delete

  Scenario: login to API
    Given user is on login page and log in
    Then verify the Statuscode

  Scenario: GetUser
    Given user is on login page and log in
    Then user get the token
    Then user get the data
    Then verify the bodycontains

  Scenario: AddUser
    Given user is on login page and log in
    Then user get the token
    Then user add the data
    Then verify the Statuscode

  Scenario: UpdateUser
    Given user is on login page and log in
    Then user get the token
    Then user update the data
    Then verify the  updated Statuscode

  Scenario: DeleteUser
    Given user is on login page and log in
    Then user get the token
    Then user delete the data
    Then verify the  deleted Statuscode
