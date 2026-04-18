# language: en
@products
Feature: Product and Checkout Functionality

  Background:
    Given the user is logged in with "standard_user" and "secret_sauce"

  @e2e @regression
  Scenario: Add a product to the cart and complete checkout
    When the user adds the product "Sauce Labs Backpack" to the cart
    And the user navigates to the cart page
    Then the user should see the product "Sauce Labs Backpack" in the cart
    When the user proceeds to checkout
    And the user fills in the checkout information with "John", "Doe", and "12345"
    And the user completes the purchase
    Then the user should see the success message "Thank you for your order!"

  @regression
  Scenario: Sort products by price from low to high
    When the user sorts the products by "Price (low to high)"
    Then the user should see the products sorted correctly by price
