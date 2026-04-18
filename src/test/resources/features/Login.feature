# language: en
@login
Feature: Login functionality for SauceDemo website

  Background:
    Given the user is on the login page

  Scenario: Successful login with valid credentials
    When the user enters the username "standard_user"
    And the user enters the password "secret_sauce"
    And the user clicks on the login button
    Then the user should see the products page

  Scenario: Failed login with a locked out user
    When the user enters the username "locked_out_user"
    And the user enters the password "secret_sauce"
    And the user clicks on the login button
    Then the user should see the error message "Epic sadface: Sorry, this user has been locked out."
