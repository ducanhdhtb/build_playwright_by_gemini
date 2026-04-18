package com.tclife.pages;

import com.microsoft.playwright.Page;

public class InventoryPage {

    private Page page;

    // 1. String Locators
    private String pageTitle = ".title";

    // 2. Page Constructor
    public InventoryPage(Page page) {
        this.page = page;
    }

    // 3. Page Actions/Methods
    public String getInventoryPageTitle() {
        return page.textContent(pageTitle);
    }
}
