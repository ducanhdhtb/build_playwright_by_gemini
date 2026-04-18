package com.tclife.tests;

import com.tclife.base.BaseTest;
import com.tclife.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test(priority = 1)
    public void homePageTitleTest() {
        HomePage homePage = new HomePage(page);
        String actualTitle = homePage.getHomePageTitle();
        Assert.assertEquals(actualTitle, "Fast and reliable end-to-end testing for modern web apps | Playwright");
    }

    @Test(priority = 2)
    public void homePageURLTest() {
        HomePage homePage = new HomePage(page);
        String actualURL = homePage.getHomePageURL();
        Assert.assertEquals(actualURL, "https://playwright.dev/");
    }

    @Test(priority = 3)
    public void searchTest() {
        HomePage homePage = new HomePage(page);
        homePage.doSearch("Java");
        // Add assertions here to verify search results
    }
}
