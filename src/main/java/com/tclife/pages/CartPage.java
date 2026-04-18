package com.tclife.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Allure;

public class CartPage {

    private Page page;

    // Locators
    private String checkoutButton = "#checkout";

    public CartPage(Page page) {
        this.page = page;
    }

    public String getProductText(String productName) {
        return page.locator(".inventory_item_name", new Page.LocatorOptions().setHasText(productName)).textContent();
    }

    public void proceedToCheckout() {
        Allure.step("Proceeding to checkout", () -> {
            page.click(checkoutButton);
        });
    }
}
