package com.tclife.pages;

import com.microsoft.playwright.Page;

public class CheckoutCompletePage {

    private Page page;

    // Locators
    private String successMessageHeader = ".complete-header";

    public CheckoutCompletePage(Page page) {
        this.page = page;
    }

    public String getSuccessMessage() {
        return page.textContent(successMessageHeader);
    }
}
