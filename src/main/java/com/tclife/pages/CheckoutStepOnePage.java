package com.tclife.pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

public class CheckoutStepOnePage {

    private Page page;

    // Locators
    private String firstNameInput = "#first-name";
    private String lastNameInput = "#last-name";
    private String postalCodeInput = "#postal-code";
    private String continueButton = "#continue";

    public CheckoutStepOnePage(Page page) {
        this.page = page;
    }

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        Allure.step("Filling checkout info: " + firstName + " " + lastName, () -> {
            page.fill(firstNameInput, firstName);
            page.fill(lastNameInput, lastName);
            page.fill(postalCodeInput, postalCode);
        });
    }

    public void continueToNextStep() {
        Allure.step("Continuing to the next checkout step", () -> {
            page.click(continueButton);
            // In a real scenario, you might navigate to CheckoutStepTwoPage here
            // For simplicity, we will assume the next step is to finish.
            page.click("#finish");
        });
    }
}
