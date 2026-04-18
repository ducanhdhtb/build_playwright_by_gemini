package com.tclife.pages;

import com.microsoft.playwright.Page;

public class HomePage {

    private Page page;

    // 1. String Locators - OR
    private String search = "button[aria-label='Search']";
    private String searchInput = "input.DocSearch-Input";
    private String nodejsLink = "//a[normalize-space()='Node.js']";


    // 2. page constructor:
    public HomePage(Page page) {
        this.page = page;
    }

    // 3. page actions/methods:
    public String getHomePageTitle() {
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getHomePageURL() {
        String url =  page.url();
        System.out.println("page url : " + url);
        return url;
    }

    public void doSearch(String productName) {
        page.click(search);
        page.fill(searchInput, productName);
        // You might need to handle search results here
    }
}
