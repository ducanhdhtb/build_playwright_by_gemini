package com.tclife.pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class LoginPage {

    private Page page;

    // 1. String Locators
    private String userName = "#user-name";
    private String password = "#password";
    private String loginButton = "#login-button";
    private String errorMessage = "h3[data-test='error']";

    // 2. Page Constructor
    public LoginPage(Page page) {
        this.page = page;
    }

    // Helper method to take and attach screenshot
    private void attachScreenshot(String name) {
        byte[] screenshot = page.screenshot();
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
    }

    // 3. Page Actions/Methods
    public String getLoginPageTitle() {
        return page.title();
    }

    public void enterUserName(String appUserName) {
        Allure.step("Filling username: " + appUserName, () -> {
            page.fill(userName, appUserName);
            attachScreenshot("After filling username");
        });
    }

    public void enterPassword(String appPassword) {
        Allure.step("Filling password", () -> {
            page.fill(password, appPassword);
            attachScreenshot("After filling password");
        });
    }

    public void clickLoginButton() {
        Allure.step("Clicking Login button", () -> {
            page.click(loginButton);
            attachScreenshot("After clicking login");
        });
    }

    public String getErrorMessage() {
        return page.textContent(errorMessage);
    }
}
