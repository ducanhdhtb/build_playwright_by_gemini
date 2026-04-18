package com.tclife.stepdefinitions;

import com.tclife.pages.InventoryPage;
import com.tclife.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private TestContext testContext;
    private LoginPage loginPage;

    public LoginSteps(TestContext context) {
        this.testContext = context;
        this.loginPage = new LoginPage(testContext.page);
    }

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        testContext.page.navigate(testContext.prop.getProperty("baseUrl"));
    }

    @When("the user enters the username {string}")
    public void the_user_enters_the_username(String username) {
        loginPage.enterUserName(username);
    }

    @When("the user enters the password {string}")
    public void the_user_enters_the_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("the user clicks on the login button")
    public void the_user_clicks_on_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("the user should see the products page")
    public void the_user_should_see_the_products_page() {
        testContext.inventoryPage = new InventoryPage(testContext.page);
        String title = testContext.inventoryPage.getInventoryPageTitle();
        Assert.assertEquals(title, "Products");
    }

    @Then("the user should see the error message {string}")
    public void the_user_should_see_the_error_message(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage));
    }
}
