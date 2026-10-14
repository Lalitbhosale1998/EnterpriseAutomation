Feature: User Login Functionality

  Background:
    Given the user is on the login page

  @smoke @positive
  Scenario: Successful login with valid credentials
    When the user enters valid username "standard_user" and password "secret_sauce"
    And clicks the login button
    Then the user should be redirected to the dashboard
    And the header title should display "Products"

  @regression @negative
  Scenario: Unsuccessful login with invalid credentials
    When the user enters invalid username "locked_out_user" and password "wrong_password"
    And clicks the login button
    Then an error message should contain "Username and password do not match"
    And the user should remain on the login page