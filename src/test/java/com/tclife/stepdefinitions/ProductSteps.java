package com.tclife.stepdefinitions;

import com.tclife.pages.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Collections;
import java.util.List;

public class ProductSteps {

    private TestContext testContext;

    public ProductSteps(TestContext context) {
        this.testContext = context;
    }

    @Given("the user is logged in with {string} and {string}")
    public void the_user_is_logged_in_with_and(String username, String password) {
        testContext.page.navigate(testContext.prop.getProperty("baseUrl"));
        LoginPage loginPage = new LoginPage(testContext.page);
        loginPage.enterUserName(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        testContext.inventoryPage = new InventoryPage(testContext.page);
    }

    @When("the user adds the product {string} to the cart")
    public void the_user_adds_the_product_to_the_cart(String productName) {
        testContext.inventoryPage.addProductToCart(productName);
    }

    @When("the user navigates to the cart page")
    public void the_user_navigates_to_the_cart_page() {
        testContext.inventoryPage.navigateToCart();
    }

    @Then("the user should see the product {string} in the cart")
    public void the_user_should_see_the_product_in_the_cart(String productName) {
        CartPage cartPage = new CartPage(testContext.page);
        Assert.assertEquals(cartPage.getProductText(productName), productName);
    }

    @When("the user proceeds to checkout")
    public void the_user_proceeds_to_checkout() {
        CartPage cartPage = new CartPage(testContext.page);
        cartPage.proceedToCheckout();
    }

    @When("the user fills in the checkout information with {string}, {string}, and {string}")
    public void the_user_fills_in_the_checkout_information_with_and(String firstName, String lastName, String postalCode) {
        CheckoutStepOnePage checkoutPage = new CheckoutStepOnePage(testContext.page);
        checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);
    }

    @When("the user completes the purchase")
    public void the_user_completes_the_purchase() {
        CheckoutStepOnePage checkoutPage = new CheckoutStepOnePage(testContext.page);
        checkoutPage.continueToNextStep();
    }

    @Then("the user should see the success message {string}")
    public void the_user_should_see_the_success_message(String successMessage) {
        CheckoutCompletePage completePage = new CheckoutCompletePage(testContext.page);
        Assert.assertEquals(completePage.getSuccessMessage(), successMessage);
    }

    @When("the user sorts the products by {string}")
    public void the_user_sorts_the_products_by(String sortOption) {
        testContext.inventoryPage.sortProductsBy(sortOption);
    }

    @Then("the user should see the products sorted correctly by price")
    public void the_user_should_see_the_products_sorted_correctly_by_price() {
        List<Double> actualPrices = testContext.inventoryPage.getProductPrices();
        List<Double> sortedPrices = new java.util.ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);
        Assert.assertEquals(actualPrices, sortedPrices, "Products are not sorted correctly by price (low to high).");
    }
}
