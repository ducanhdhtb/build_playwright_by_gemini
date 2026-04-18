package com.tclife.tests;

import com.tclife.base.BaseTest;
import com.tclife.pages.InventoryPage;
import com.tclife.pages.LoginPage;
import com.tclife.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @DataProvider
    public Object[][] getLoginData() {
        return ExcelUtil.getTestData("LoginData");
    }

    @Test(dataProvider = "getLoginData")
    public void loginTest(String username, String password, String expectedResult) {
        // Navigate to the base URL before each test run
        page.navigate(prop.getProperty("baseUrl"));
        
        LoginPage loginPage = new LoginPage(page);
        loginPage.doLogin(username, password);

        if (expectedResult.equals("success")) {
            InventoryPage inventoryPage = new InventoryPage(page);
            String inventoryTitle = inventoryPage.getInventoryPageTitle();
            Assert.assertEquals(inventoryTitle, "Products", "Login should be successful and navigate to inventory page.");
        } else {
            String actualErrorMessage = loginPage.getErrorMessage();
            Assert.assertTrue(actualErrorMessage.contains(expectedResult), "Error message mismatch for failed login.");
        }
    }
}
