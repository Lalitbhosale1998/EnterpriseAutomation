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

  @datadriven
  Scenario Outline: Login  <TestCaseName>
    When the user logs in using test data from sheet "LoginData" for case "<TestCaseName>"
    Then the login result should match expected test data from sheet "LoginData" for case "<TestCaseName>"

    Examples:
      | TestCaseName        |
      | ValidLoginTest      |
      | LockedUserTest      |
      | InvalidPasswordTest |
      | ValidLoginTest_All_Items  |

  @products
  Scenario: Retrieve and verify all products on dashboard
    Given the user is on the login page
    When the user enters valid username "standard_user" and password "secret_sauce"
    And clicks the login button
    Then the user should be redirected to the dashboard
    And all product names should be retrieved and printed
    And the total number of products should be 6

  @hamburgerMenuButton
  Scenario: Successful login with valid credentials
    When the user enters valid username "standard_user" and password "secret_sauce"
    And clicks the login button
    Then the user should be redirected to the dashboard
    And clicks the hamburger Menu Button